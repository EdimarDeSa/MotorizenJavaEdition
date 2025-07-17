package com.efscode.motorizen_backend.configurations;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.efscode.motorizen_backend.enums.MotoriZenResponseCodeEnum;
import com.efscode.motorizen_backend.errors.MotorizenException;
import com.efscode.motorizen_backend.models.user.UserEntity;
import com.efscode.motorizen_backend.models.user.UserMapper;
import com.efscode.motorizen_backend.services.AuthService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class TokenAuthFilter extends OncePerRequestFilter {
  @Autowired
  private AuthService authService;

  @Autowired
  private UserMapper userMapper;

  @Override
  protected void doFilterInternal(@NonNull HttpServletRequest request,
      @NonNull HttpServletResponse response,
      @NonNull FilterChain filterChain) {
    log.debug("Iniciando validação de login");
    try {
      String token = recoverToken(request);

      DecodedJWT decodedJWT = authService.decodeAndValidateToken(token);

      UUID userId = UUID.fromString(decodedJWT.getSubject());

      UserEntity user = authService.selectUserByIdAndDeletedAtIsNull(userId);

      log.info("Usuário validado: `{}`", user.getEmail());

      var authorities = user.getAuthorities();
      log.info("Autoridades do usuário: `{}`", authorities);
      var auth = new UsernamePasswordAuthenticationToken(userMapper.entityToDto(user), null, authorities);
      SecurityContextHolder.getContext().setAuthentication(auth);

      filterChain.doFilter(request, response);

    } catch (IOException e) {
      throw new MotorizenException(MotoriZenResponseCodeEnum.TOKEN_INVALID);
    } catch (ServletException e) {
      throw new MotorizenException(MotoriZenResponseCodeEnum.TOKEN_INVALID);
    }
  }

  @Override
  protected boolean shouldNotFilter(@NonNull HttpServletRequest request) {
    var uri = request.getRequestURI();

    log.info("Iniciando filtro de autenticação para url `{}`", uri);

    List<String> uris = List.of("/auth/login",
        "/auth/renew-token",
        "/user/new",
        "/swagger-ui",
        "/v3/api-docs",
        "/error");

    for (String u : uris) {
      if (uri.startsWith("/api" + u))
        return true;
    }
    return false;
  }

  private String recoverToken(HttpServletRequest request) {
    log.debug("Recuperando token da requisição.");

    String bearerToken = request.getHeader("Authorization");

    if (bearerToken == null ||
        bearerToken.isBlank() ||
        !bearerToken.startsWith("Bearer ")) {
      throw new MotorizenException(MotoriZenResponseCodeEnum.TOKEN_INVALID);
    }

    return bearerToken.substring(7);

  }

}
