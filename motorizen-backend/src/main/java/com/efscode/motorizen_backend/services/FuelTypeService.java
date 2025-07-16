package com.efscode.motorizen_backend.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.efscode.motorizen_backend.Utils.Validators;
import com.efscode.motorizen_backend.enums.MotoriZenResponseCodeEnum;
import com.efscode.motorizen_backend.errors.MotorizenException;
import com.efscode.motorizen_backend.models.dtos.FuelTypeDTO;
import com.efscode.motorizen_backend.models.dtos.NewFuelType;
import com.efscode.motorizen_backend.models.entitys.FuelTypeEntity;
import com.efscode.motorizen_backend.repositorys.FuelTypeRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FuelTypeService {
  private final FuelTypeRepository fuelTypeRepo;
  private final Validators validators;

  public void createNewFuelType(NewFuelType newFuelType) {
    try {
      validators.validateFuelType(newFuelType.toDTO());
      FuelTypeEntity fuelType = new FuelTypeEntity(newFuelType);
      fuelTypeRepo.save(fuelType);
    } catch (IllegalArgumentException e) {
      throw new MotorizenException(MotoriZenResponseCodeEnum.INVALID_FUEL_TYPE_NAME);
    }
  }

  public List<FuelTypeDTO> findAllFuelTypes() {
    var fuelTypes = fuelTypeRepo.findAll();
    if (fuelTypes.isEmpty())
      throw new MotorizenException(MotoriZenResponseCodeEnum.FUEL_TYPE_NOT_FOUND);
    return fuelTypes.stream()
        .map(FuelTypeEntity::toDTO)
        .toList();
  }
}
