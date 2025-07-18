package com.efscode.motorizen_backend.repositorys;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efscode.motorizen_backend.models.backlog.BackLogEntity;

@Repository
public interface BackLogRepository extends JpaRepository<BackLogEntity, Integer> {
}
