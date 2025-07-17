package com.efscode.motorizen_backend.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.efscode.motorizen_backend.enums.MotoriZenResponseCodeEnum;
import com.efscode.motorizen_backend.errors.MotorizenException;
import com.efscode.motorizen_backend.models.fuel_type.FuelTypeDTO;
import com.efscode.motorizen_backend.models.fuel_type.FuelTypeEntity;
import com.efscode.motorizen_backend.models.fuel_type.FuelTypeMapper;
import com.efscode.motorizen_backend.models.fuel_type.FuelTypeUpdatesDTO;
import com.efscode.motorizen_backend.models.fuel_type.NewFuelTypeDTO;
import com.efscode.motorizen_backend.repositorys.FuelTypeRepository;
import com.efscode.motorizen_backend.utils.Validators;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FuelTypeService {
  private final FuelTypeRepository fuelTypeRepo;
  private final Validators validators;

  public List<FuelTypeDTO> findAllFuelTypes() {
    List<FuelTypeEntity> fuelTypes = fuelTypeRepo.findAll();

    if (fuelTypes.isEmpty())
      throw new MotorizenException(MotoriZenResponseCodeEnum.FUEL_TYPE_NOT_FOUND);

    return FuelTypeMapper.INSTANCE.entitiesToDtos(fuelTypes);
  }

  public List<FuelTypeDTO> filterFuelTypes(String filter) {
    List<FuelTypeEntity> fuelTypes = fuelTypeRepo.findByNameContainingIgnoreCaseAndDeletedAtIsNull(filter);

    if (fuelTypes.isEmpty())
      throw new MotorizenException(MotoriZenResponseCodeEnum.FUEL_TYPE_NOT_FOUND);

    return FuelTypeMapper.INSTANCE.entitiesToDtos(fuelTypes);

  }

  public void createFuelType(NewFuelTypeDTO newFuelType) {
    try {
      validators.validateNewFuelType(FuelTypeMapper.INSTANCE.newDtoToDto(newFuelType));

      FuelTypeEntity fuelType = createNewOrReactivateFuelType(newFuelType);

      fuelTypeRepo.save(fuelType);

    } catch (IllegalArgumentException e) {
      throw new MotorizenException(MotoriZenResponseCodeEnum.INVALID_FUEL_TYPE_NAME);
    }
  }

  public FuelTypeDTO updateFuelType(Integer id, FuelTypeUpdatesDTO fuelTypeUpdates) {
    validators.validateFuelTypeUpdates(
        FuelTypeMapper.INSTANCE.updatesDtoToDto(fuelTypeUpdates), id);

    FuelTypeEntity fuelType = fuelTypeRepo.findByIdAndDeletedAtIsNull(id);

    if (fuelType == null)
      throw new MotorizenException(MotoriZenResponseCodeEnum.FUEL_TYPE_ALREADY_EXISTS);

    FuelTypeMapper.INSTANCE.updateEntityFromDto(fuelTypeUpdates, fuelType);

    fuelTypeRepo.save(fuelType);

    return FuelTypeMapper.INSTANCE.entityToDto(fuelType);
  }

  public void deleteFuelType(Integer id) {
    FuelTypeEntity fuelType = fuelTypeRepo.findByIdAndDeletedAtIsNull(id);

    if (fuelType == null)
      throw new MotorizenException(MotoriZenResponseCodeEnum.FUEL_TYPE_NOT_FOUND);

    fuelType.setDeletedAt(java.time.LocalDateTime.now());

    fuelTypeRepo.save(fuelType);
  }

  private FuelTypeEntity createNewOrReactivateFuelType(NewFuelTypeDTO newFuelType) {
    FuelTypeEntity fuelType;

    if (fuelTypeRepo.existsByNameAndDeletedAtIsNotNull(newFuelType.name())) {
      fuelType = fuelTypeRepo.findByName(newFuelType.name());

      fuelType.setDeletedAt(null);

    } else {
      fuelType = FuelTypeMapper.INSTANCE.newDtoToEntity(newFuelType);
    }

    return fuelType;
  }
}
