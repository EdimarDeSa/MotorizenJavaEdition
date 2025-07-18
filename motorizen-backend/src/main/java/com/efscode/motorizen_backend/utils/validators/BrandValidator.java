package com.efscode.motorizen_backend.utils.validators;

import org.springframework.stereotype.Component;

import com.efscode.motorizen_backend.enums.MotoriZenResponseCodeEnum;
import com.efscode.motorizen_backend.models.brand.BrandUpdatesDTO;
import com.efscode.motorizen_backend.models.brand.NewBrandDTO;
import com.efscode.motorizen_backend.repositories.BrandRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class BrandValidator extends BaseValidators {
  private final BrandRepository brandRepo;

  public void validateNewBrand(NewBrandDTO dto) {
    if (!isValidLength(dto.name(), 2, 50))
      throwException(MotoriZenResponseCodeEnum.INVALID_BRAND_NAME);

    if (brandRepo.existsByNameAndDeletedAtIsNull(dto.name()))
      throwException(MotoriZenResponseCodeEnum.BRAND_ALREADY_EXISTS);
  }

  public void validateBrandUpdates(BrandUpdatesDTO dto, Integer id) {
    if (!isValidLength(dto.name(), 2, 50))
      throwException(MotoriZenResponseCodeEnum.INVALID_BRAND_NAME);

    if (brandRepo.existsByNameAndIdNot(dto.name(), id))
      throwException(MotoriZenResponseCodeEnum.BRAND_ALREADY_EXISTS);
  }
}
