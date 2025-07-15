package com.efscode.motorizen_backend.repositorys;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efscode.motorizen_backend.models.entitys.RegisterEntity;

@Repository
public interface RegisterRepository extends JpaRepository<RegisterEntity, UUID> {

}
