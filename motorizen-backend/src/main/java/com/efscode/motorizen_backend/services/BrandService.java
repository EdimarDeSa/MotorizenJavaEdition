package com.efscode.motorizen_backend.services;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

import com.efscode.motorizen_backend.Utils.Validators;
import com.efscode.motorizen_backend.enums.MotoriZenResponseCodeEnum;
import com.efscode.motorizen_backend.errors.MotorizenException;
import com.efscode.motorizen_backend.models.dtos.BrandDTO;
import com.efscode.motorizen_backend.models.dtos.BrandUpdatesDTO;
import com.efscode.motorizen_backend.models.dtos.NewBrandDTO;
import com.efscode.motorizen_backend.models.entitys.BrandEntity;
import com.efscode.motorizen_backend.repositorys.BrandRepository;

@Service
@RequiredArgsConstructor
public class BrandService {
  private final BrandRepository brandRepo;
  private final Validators validators;

  public List<BrandDTO> findAllBrands() {
    var brands = brandRepo.findAll();
    if (brands.isEmpty())
      throw new MotorizenException(MotoriZenResponseCodeEnum.BRAND_NOT_FOUND);
    return brands.stream()
        .map(BrandEntity::toDTO)
        .toList();
  }

  public void createNewBrand(NewBrandDTO newBrand) {
    try {
      validators.validateNewBrand(newBrand.toDTO());
      BrandEntity brand = new BrandEntity(newBrand);
      brandRepo.save(brand);
    } catch (IllegalArgumentException e) {
      throw new MotorizenException(MotoriZenResponseCodeEnum.INVALID_BRAND_NAME);
    }
  }

  public BrandDTO updateBrand(Integer id, BrandUpdatesDTO brandUpdates) {
    BrandEntity brand = brandRepo.findById(id)
        .orElseThrow(() -> new MotorizenException(MotoriZenResponseCodeEnum.BRAND_NOT_FOUND));

    if (brand.getDeletedAt() != null)
      throw new MotorizenException(MotoriZenResponseCodeEnum.BRAND_NOT_FOUND);

    if (brandRepo.existsByNameAndIdNot(brandUpdates.name(), id))
      throw new MotorizenException(MotoriZenResponseCodeEnum.BRAND_ALREADY_EXISTS);

    if (brandUpdates.name() != null && !brandUpdates.name().isBlank()) {
      brand.setName(brandUpdates.name());
    }

    validators.validateBrand(brand.toDTO());

    brandRepo.save(brand);

    return brand.toDTO();
  }

  public void deleteBrand(Integer id) {
    BrandEntity brand = brandRepo.findById(id)
        .orElseThrow(() -> new MotorizenException(MotoriZenResponseCodeEnum.BRAND_NOT_FOUND));

    brand.setDeletedAt(LocalDateTime.now());

    brandRepo.save(brand);
  }
}
