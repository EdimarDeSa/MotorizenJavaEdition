package com.efscode.motorizen_backend.services;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.efscode.motorizen_backend.enums.MotoriZenResponseCodeEnum;
import com.efscode.motorizen_backend.errors.MotorizenException;
import com.efscode.motorizen_backend.models.user.NewUserDTO;
import com.efscode.motorizen_backend.models.user.UserEntity;
import com.efscode.motorizen_backend.models.user.UserMapper;
import com.efscode.motorizen_backend.repositorys.UserRepository;
import com.efscode.motorizen_backend.utils.Validators;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
  private final UserRepository userRepo;
  private final Validators validators;
  private final PasswordEncoder passwordEncoder;
  private final UserMapper userMapper;

  public void createNewUser(NewUserDTO newUser) {
    try {
      validators.validateNewUser(userMapper.newDtoToDto(newUser), newUser.password());

      String passwordHash = passwordEncoder.encode(newUser.password());
      UserEntity user = userMapper.newDtoToEntity(newUser, passwordHash);

      userRepo.save(user);
    } catch (IllegalArgumentException e) {
      log.error(e.getMessage());
      throw new MotorizenException(MotoriZenResponseCodeEnum.UNKNOWN_ERROR);
    }
  }
}
