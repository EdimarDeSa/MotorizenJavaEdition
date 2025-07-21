package com.efscode.motorizen_backend.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.efscode.motorizen_backend.models.vehicle.VehicleEntity;

@Repository
public interface VehicleRepository extends JpaRepository<VehicleEntity, UUID>, JpaSpecificationExecutor<VehicleEntity> {
  List<VehicleEntity> findAllByUserId(UUID userId);

  VehicleEntity findByIdAndUserId(UUID id, UUID userId);

  VehicleEntity findByIdAndUserIdAndDeletedAtIsNull(UUID id, UUID userId);

  Boolean existsByRenavam(String renavam);

  Boolean existsByLicensePlate(String licensePlate);

  Boolean existsByRenavamAndIdNot(String renavam, UUID id);

  Boolean existsByLicensePlateAndIdNot(String licensePlate, UUID id);
}
