package com.efscode.motorizen_backend.repositorys;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efscode.motorizen_backend.models.brand.BrandEntity;

@Repository
public interface BrandRepository extends JpaRepository<BrandEntity, Integer> {
  BrandEntity findByIdAndDeletedAtIsNull(Integer id);

  BrandEntity findByName(String name);

  List<BrandEntity> findByDeletedAtIsNull();

  List<BrandEntity> findByNameContainingIgnoreCaseAndDeletedAtIsNull(String name);

  Boolean existsByNameAndDeletedAtIsNull(String name);

  Boolean existsByNameAndDeletedAtIsNotNull(String name);

  Boolean existsByNameAndIdNot(String name, Integer id);
}
