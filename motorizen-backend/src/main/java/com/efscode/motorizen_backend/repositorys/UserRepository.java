package com.efscode.motorizen_backend.repositorys;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efscode.motorizen_backend.models.entitys.UserEntity;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, UUID> {
}
