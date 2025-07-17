package com.efscode.motorizen_backend.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.efscode.motorizen_backend.Utils.Validators;
import com.efscode.motorizen_backend.enums.MotoriZenResponseCodeEnum;
import com.efscode.motorizen_backend.errors.MotorizenException;
import com.efscode.motorizen_backend.models.fuel_type.FuelTypeDTO;
import com.efscode.motorizen_backend.models.fuel_type.FuelTypeEntity;
import com.efscode.motorizen_backend.models.fuel_type.FuelTypeUpdatesDTO;
import com.efscode.motorizen_backend.models.fuel_type.NewFuelTypeDTO;
import com.efscode.motorizen_backend.repositorys.FuelTypeRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FuelTypeService {
  private final FuelTypeRepository fuelTypeRepo;
  private final Validators validators;

  public List<FuelTypeDTO> findAllFuelTypes() {
    var fuelTypes = fuelTypeRepo.findAll();
    if (fuelTypes.isEmpty())
      throw new MotorizenException(MotoriZenResponseCodeEnum.FUEL_TYPE_NOT_FOUND);
    return fuelTypes.stream()
        .map(FuelTypeEntity::toDTO)
        .toList();
  }

  public List<FuelTypeDTO> filterFuelTypes(String filter) {
    var fuelTypes = fuelTypeRepo.findByNameContainingIgnoreCaseAndDeletedAtIsNull(filter);
    if (fuelTypes.isEmpty())
      throw new MotorizenException(MotoriZenResponseCodeEnum.FUEL_TYPE_NOT_FOUND);
    return fuelTypes.stream()
        .map(FuelTypeEntity::toDTO)
        .toList();
  }

  public void createNewFuelType(NewFuelTypeDTO newFuelType) {
    try {
      validators.validateFuelType(newFuelType.toDTO());
      FuelTypeEntity fuelType = new FuelTypeEntity(newFuelType);
      fuelTypeRepo.save(fuelType);
    } catch (IllegalArgumentException e) {
      throw new MotorizenException(MotoriZenResponseCodeEnum.INVALID_FUEL_TYPE_NAME);
    }
  }

  public FuelTypeDTO updateFuelType(Integer id, FuelTypeUpdatesDTO fuelTypeUpdates) {
    FuelTypeEntity fuelType = fuelTypeRepo.findByIdAndDeletedAtIsNull(id);

    if (fuelTypeRepo.existsByNameAndIdNot(fuelTypeUpdates.name(), id))
      throw new MotorizenException(MotoriZenResponseCodeEnum.FUEL_TYPE_ALREADY_EXISTS);

    if (fuelTypeUpdates.name() != null && !fuelTypeUpdates.name().isBlank()) {
      fuelType.setName(fuelTypeUpdates.name());
    }

    validators.validateFuelType(fuelType.toDTO());

    fuelTypeRepo.save(fuelType);

    return fuelType.toDTO();
  }

  public void deleteFuelType(Integer id) {
    FuelTypeEntity fuelType = fuelTypeRepo.findByIdAndDeletedAtIsNull(id);
    fuelType.setDeletedAt(java.time.LocalDateTime.now());
    fuelTypeRepo.save(fuelType);
  }
}
