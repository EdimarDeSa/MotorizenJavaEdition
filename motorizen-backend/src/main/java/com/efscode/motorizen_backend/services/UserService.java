package com.efscode.motorizen_backend.services;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.efscode.motorizen_backend.Utils.Validators;
import com.efscode.motorizen_backend.enums.MotoriZenResponseCodeEnum;
import com.efscode.motorizen_backend.errors.MotorizenException;
import com.efscode.motorizen_backend.models.dtos.NewUser;
import com.efscode.motorizen_backend.models.entitys.UserEntity;
import com.efscode.motorizen_backend.repositorys.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
  private final UserRepository userRepo;
  private final Validators validators;
  private final PasswordEncoder passwordEncoder;

  public void createNewUser(NewUser newUser) {
    try {
      validators.validateNewUser(newUser);
      String passwordHash = passwordEncoder.encode(newUser.getPassword());

      UserEntity user = new UserEntity(newUser, passwordHash);

      userRepo.save(user);
    } catch (IllegalArgumentException e) {
      log.error(e.getMessage());
      throw new MotorizenException(MotoriZenResponseCodeEnum.UNKNOWN_ERROR);
    }
  }
}
