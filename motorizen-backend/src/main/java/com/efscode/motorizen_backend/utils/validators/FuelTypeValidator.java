package com.efscode.motorizen_backend.utils.validators;

import org.springframework.stereotype.Component;

import com.efscode.motorizen_backend.enums.MotoriZenResponseCodeEnum;
import com.efscode.motorizen_backend.models.fuel_type.FuelTypeUpdatesDTO;
import com.efscode.motorizen_backend.models.fuel_type.NewFuelTypeDTO;
import com.efscode.motorizen_backend.repositorys.FuelTypeRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Component
public class FuelTypeValidator extends BaseValidators {
  private final FuelTypeRepository fuelTypeRepo;

  public void validateNewFuelType(NewFuelTypeDTO dto) {
    if (!isValidLength(dto.name(), 2, 20))
      throwException(MotoriZenResponseCodeEnum.INVALID_FUEL_TYPE_NAME);

    if (fuelTypeRepo.existsByNameAndDeletedAtIsNull(dto.name())) {
      throwException(MotoriZenResponseCodeEnum.FUEL_TYPE_ALREADY_EXISTS);
    }
  }

  public void validateFuelTypeUpdates(FuelTypeUpdatesDTO dto, Integer id) {
    if (!isValidLength(dto.name(), 2, 20))
      throwException(MotoriZenResponseCodeEnum.INVALID_FUEL_TYPE_NAME);

    if (fuelTypeRepo.existsByNameAndIdNot(dto.name(), id))
      throwException(MotoriZenResponseCodeEnum.FUEL_TYPE_ALREADY_EXISTS);
  }
}
