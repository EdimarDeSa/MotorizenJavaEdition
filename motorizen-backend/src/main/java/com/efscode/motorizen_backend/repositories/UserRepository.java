package com.efscode.motorizen_backend.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efscode.motorizen_backend.models.user.UserEntity;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, UUID> {
  public UserEntity findByIdAndDeletedAtIsNull(UUID userId);

  public UserEntity findByEmail(String email);

  public Boolean existsByEmail(String email);

  public Boolean existsByEmailAndIdNot(String email, UUID id);
}
