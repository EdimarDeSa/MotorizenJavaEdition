package com.efscode.motorizen_backend.repositorys;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efscode.motorizen_backend.models.entitys.BrandEntity;

@Repository
public interface BrandRepository extends JpaRepository<BrandEntity, Integer> {
  List<BrandEntity> findByDeletedAtIsNull();

  List<BrandEntity> findByNameContainingIgnoreCase(String name);

  Boolean existsByName(String name);

  Boolean existsByNameAndIdNot(String name, Integer id);

  Integer findIdByName(String name);
}
