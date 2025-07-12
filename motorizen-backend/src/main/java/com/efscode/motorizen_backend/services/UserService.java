package com.efscode.motorizen_backend.services;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.efscode.motorizen_backend.models.dtos.UserDTO;
import com.efscode.motorizen_backend.models.entitys.UserEntity;
import com.efscode.motorizen_backend.repositorys.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
  private final UserRepository userRepo;

  public UserDTO getMe(UUID user_id) {
    try {
      UserEntity user = userRepo.findById(user_id).get();

      return toUserDTO(user);

    } catch (NoSuchElementException e) {
      log.error(null, e);
      throw new NoSuchElementException("User not found");
    }

  }

  private UserDTO toUserDTO(UserEntity user) {
    return UserDTO.builder()
        .id(user.getId())
        .firstName(user.getFirstName())
        .lastName(user.getLastName())
        .email(user.getEmail())
        .birthdate(user.getBirthdate())
        .isActive(user.getIsActive())
        .isAdministrator(user.getIsAdministrator())
        .build();
  }

}
