package com.efscode.motorizen_backend.services;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.efscode.motorizen_backend.enums.MotoriZenResponseCodeEnum;
import com.efscode.motorizen_backend.errors.MotorizenException;
import com.efscode.motorizen_backend.models.user.NewUserDTO;
import com.efscode.motorizen_backend.models.user.UserDTO;
import com.efscode.motorizen_backend.models.user.UserEntity;
import com.efscode.motorizen_backend.models.user.UserMapper;
import com.efscode.motorizen_backend.models.user.UserUpdateEmailDTO;
import com.efscode.motorizen_backend.models.user.UserUpdatePasswordDTO;
import com.efscode.motorizen_backend.models.user.UserUpdatesDTO;
import com.efscode.motorizen_backend.repositories.UserRepository;
import com.efscode.motorizen_backend.utils.validators.UserValidator;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
  private final UserRepository userRepo;
  private final UserValidator validator;
  private final PasswordEncoder passwordEncoder;
  private final UserMapper userMapper;

  public void createNewUser(NewUserDTO newUser) {
    log.debug("Iniciando createNewUser para o usuário`{}`", newUser.email());

    try {
      validator.validateNewUser(newUser);

      UserEntity user = userMapper.newDtoToEntity(newUser, passwordEncoder.encode(newUser.password()));

      userRepo.save(user);
    } catch (IllegalArgumentException e) {
      log.error(e.getMessage());
      throw new MotorizenException(MotoriZenResponseCodeEnum.UNKNOWN_ERROR);
    }
  }

  public UserDTO updateUser(UUID id, UserUpdatesDTO userUpdate) {
    log.debug("Iniciando updateUser para o usuário`{}`", id);

    validator.validateUserUpdates(userUpdate);

    UserEntity user = userRepo.findByIdAndDeletedAtIsNull(id);

    if (user == null)
      throw new MotorizenException(MotoriZenResponseCodeEnum.USER_NOT_FOUND);

    userMapper.updateEntityFromDto(userUpdate, user);
    userRepo.save(user);

    return userMapper.entityToDto(user);
  }

  public UserDTO updateUserEmail(UUID id, UserUpdateEmailDTO emailUpdate) {
    log.debug("Iniciando updateUserEmail para o usuário`{}`", id);

    validator.validateNewEmail(emailUpdate, id);

    UserEntity user = userRepo.findByIdAndDeletedAtIsNull(id);

    if (user == null)
      throw new MotorizenException(MotoriZenResponseCodeEnum.USER_NOT_FOUND);

    user.setEmail(emailUpdate.email().toLowerCase());
    userRepo.save(user);

    return userMapper.entityToDto(user);
  }

  public void updateUserPassword(UUID id, UserUpdatePasswordDTO passwordUpdate) {
    log.debug("Iniciando updateUserPassword para o usuário`{}`", id);

    validator.validateNewPassword(passwordUpdate.password());

    UserEntity user = userRepo.findByIdAndDeletedAtIsNull(id);

    if (user == null)
      throw new MotorizenException(MotoriZenResponseCodeEnum.USER_NOT_FOUND);

    user.setPassword(passwordEncoder.encode(passwordUpdate.password()));
    userRepo.save(user);
  }

  public void deleteUser(UUID id) {
    log.debug("Iniciando deleteUser para o usuário`{}`", id);

    UserEntity user = userRepo.findByIdAndDeletedAtIsNull(id);

    if (user == null)
      throw new MotorizenException(MotoriZenResponseCodeEnum.USER_NOT_FOUND);

    user.setDeletedAt(LocalDateTime.now());

    userRepo.save(user);
  }

  public UserEntity selectUserById(UUID id) {
    UserEntity user = userRepo.findByIdAndDeletedAtIsNull(id);

    if (user == null)
      throw new MotorizenException(MotoriZenResponseCodeEnum.USER_NOT_FOUND);

    return user;
  }
}
