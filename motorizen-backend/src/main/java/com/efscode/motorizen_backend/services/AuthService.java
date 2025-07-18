package com.efscode.motorizen_backend.services;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.efscode.motorizen_backend.enums.MotoriZenResponseCodeEnum;
import com.efscode.motorizen_backend.errors.MotorizenException;
import com.efscode.motorizen_backend.models.auth.LoginDTO;
import com.efscode.motorizen_backend.models.auth.TokenDTO;
import com.efscode.motorizen_backend.models.user.UserDTO;
import com.efscode.motorizen_backend.models.user.UserEntity;
import com.efscode.motorizen_backend.models.user.UserMapper;
import com.efscode.motorizen_backend.repositories.UserRepository;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {
  private final UserMapper userMapper;
  private final UserRepository userRepo;
  private final PasswordEncoder passwordEncoder;

  @Value("${app.security.jwt.secret}")
  private String jwtSecret;

  @Value("${app.security.jwt.expiration}")
  private Integer jwtExpiration;

  @Value("${app.security.jwt.refreshSecret}")
  private String jwtRefreshSecret;

  @Value("${app.security.jwt.refreshExpiration}")
  private Integer jwtRefreshExpiration;

  @Value("${app.security.jwt.issuer}")
  private String jwtIssuer;

  private Algorithm jwtSecretAlg;
  private Algorithm jwtRefreshSecretAlg;

  @PostConstruct
  public void init() {
    this.jwtSecretAlg = Algorithm.HMAC256(jwtSecret);
    this.jwtRefreshSecretAlg = Algorithm.HMAC256(jwtRefreshSecret);
  }

  public TokenDTO login(LoginDTO loginDTO) {
    UserEntity user = userRepo.findByEmail(loginDTO.login());

    if (user == null
        || !passwordEncoder.matches(loginDTO.password(), user.getPassword())
        || !user.getIsActive()) {
      throw new MotorizenException(MotoriZenResponseCodeEnum.INVALID_CREDENTIALS);
    }

    Instant expirationTime = getExpirationTime(jwtExpiration);
    String token = generateToken(userMapper.entityToDto(user), expirationTime);

    Instant refreshTokenExpirationTime = getExpirationTime(jwtRefreshExpiration);
    String refreshToken = generateRefreshToken(user.getId().toString(), refreshTokenExpirationTime);

    TokenDTO tokenDTO = new TokenDTO(
        token,
        refreshToken,
        expirationTime.toString(),
        refreshTokenExpirationTime.toString());

    return tokenDTO;

  }

  private String generateToken(UserDTO user, Instant expirationTime) {
    log.info(expirationTime.toString());
    try {
      Instant now = Instant.now();
      String token = JWT.create()
          .withIssuer(jwtIssuer)
          .withSubject(user.id().toString())
          .withClaim("firstName", user.firstName())
          .withClaim("lastName", user.lastName())
          .withClaim("email", user.email())
          .withClaim("birthdate", user.birthdate().toString())
          .withClaim("isAdministrator", user.isAdministrator())
          .withClaim("isActive", user.isActive())
          .withClaim("fullName", user.getFullName())
          .withClaim("initials", user.getInitials())
          .withExpiresAt(expirationTime)
          .withIssuedAt(now)
          .sign(jwtSecretAlg);

      return token;
    } catch (JWTCreationException e) {
      throw new MotorizenException(MotoriZenResponseCodeEnum.INVALID_CREDENTIALS);
    }
  }

  private String generateRefreshToken(String user_id, Instant expirationTime) {
    try {
      Instant now = Instant.now();
      String token = JWT.create()
          .withIssuer(jwtIssuer)
          .withSubject(user_id)
          .withExpiresAt(expirationTime)
          .withIssuedAt(now)
          .sign(jwtRefreshSecretAlg);

      return token;
    } catch (JWTCreationException e) {
      throw new MotorizenException(MotoriZenResponseCodeEnum.INVALID_CREDENTIALS);
    }
  }

  private Instant getExpirationTime(Integer expiration) {
    return Instant.now().plus(expiration, ChronoUnit.MINUTES);
  }

  public DecodedJWT decodeAndValidateToken(String token) {
    JWTVerifier verifier = JWT.require(jwtSecretAlg)
        .withIssuer(jwtIssuer)
        .withClaimPresence("firstName")
        .withClaimPresence("lastName")
        .withClaimPresence("email")
        .withClaimPresence("birthdate")
        .withClaimPresence("isAdministrator")
        .withClaimPresence("isActive")
        .withClaimPresence("fullName")
        .withClaimPresence("initials")
        .build();

    DecodedJWT decodedJWT = verifier.verify(token);

    log.debug("JWT decodificado com sucesso: <DecodedJWT: {}>", decodedJWT);

    return decodedJWT;
  }

  public UserEntity selectUserByIdAndDeletedAtIsNull(UUID userId) {
    UserEntity user = userRepo.findByIdAndDeletedAtIsNull(userId);

    if (user == null)
      throw new MotorizenException(MotoriZenResponseCodeEnum.USER_NOT_FOUND);

    return user;
  }

  public TokenDTO renewToken(String refreshToken) {
    try {
      JWTVerifier verifier = JWT.require(jwtRefreshSecretAlg)
          .withIssuer(jwtIssuer)
          .build();

      DecodedJWT decodedJWT = verifier.verify(refreshToken);
      String userId = decodedJWT.getSubject();

      UserEntity user = selectUserByIdAndDeletedAtIsNull(UUID.fromString(userId));

      Instant expirationTime = getExpirationTime(jwtExpiration);
      String newToken = generateToken(userMapper.entityToDto(user), expirationTime);

      Instant newRefreshTokenExpirationTime = getExpirationTime(jwtRefreshExpiration);
      String newRefreshToken = generateRefreshToken(userId, newRefreshTokenExpirationTime);

      return new TokenDTO(
          newToken,
          newRefreshToken,
          expirationTime.toString(),
          newRefreshTokenExpirationTime.toString());

    } catch (Exception e) {
      log.error("Erro ao renovar o token: {}", e.getMessage());
      throw new MotorizenException(MotoriZenResponseCodeEnum.INVALID_REFRESH_TOKEN);
    }
  }

  public void logout() {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'logout'");
  }

}
