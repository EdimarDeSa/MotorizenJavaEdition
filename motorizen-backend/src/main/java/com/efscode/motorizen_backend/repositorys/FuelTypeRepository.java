package com.efscode.motorizen_backend.repositorys;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efscode.motorizen_backend.models.fuel_type.FuelTypeEntity;

@Repository
public interface FuelTypeRepository extends JpaRepository<FuelTypeEntity, Integer> {
  FuelTypeEntity findByIdAndDeletedAtIsNull(Integer id);

  List<FuelTypeEntity> findByDeletedAtIsNull();

  List<FuelTypeEntity> findByNameContainingIgnoreCaseAndDeletedAtIsNull(String name);

  Boolean existsByName(String name);

  Boolean existsByNameAndIdNot(String name, Integer id);

  Integer findIdByName(String name);
}
