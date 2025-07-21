package com.efscode.motorizen_backend.utils.validators;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.efscode.motorizen_backend.enums.MotoriZenResponseCodeEnum;
import com.efscode.motorizen_backend.models.vehicle.NewVehicleDTO;
import com.efscode.motorizen_backend.models.vehicle.VehicleUpdatesDTO;
import com.efscode.motorizen_backend.repositories.BrandRepository;
import com.efscode.motorizen_backend.repositories.FuelTypeRepository;
import com.efscode.motorizen_backend.repositories.VehicleRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class VehicleValidator extends BaseValidators {
  private final VehicleRepository vehicleRepo;
  private final BrandRepository brandRepo;
  private final FuelTypeRepository fuelTypeRepo;

  public void validateNewVehicle(NewVehicleDTO dto) {

    if (!brandRepo.existsById(dto.brandId()))
      throwException(MotoriZenResponseCodeEnum.BRAND_NOT_FOUND);

    if (!fuelTypeRepo.existsById(dto.fuelTypeId()))
      throwException(MotoriZenResponseCodeEnum.FUEL_TYPE_NOT_FOUND);

    if (!isValidLength(dto.model(), 2, 100))
      throwException(MotoriZenResponseCodeEnum.INVALID_VEHICLE_MODEL);

    if (!isValidLength(dto.renavam(), 11, 11))
      throwException(MotoriZenResponseCodeEnum.INVALID_VEHICLE_RENAVAM);

    if (!vehicleRepo.existsByRenavam(dto.renavam()))
      throwException(MotoriZenResponseCodeEnum.VEHICLE_RENAVAM_ALREADY_EXISTS);

    if (dto.year() < 1900 || dto.year() > LocalDate.now().plusYears(1).getYear())
      throwException(MotoriZenResponseCodeEnum.INVALID_VEHICLE_YEAR);

    if (!isValidLength(dto.color(), 3, 25))
      throwException(MotoriZenResponseCodeEnum.INVALID_VEHICLE_COLOR);

    if (!isValidLength(dto.licensePlate(), 7, 10))
      throwException(MotoriZenResponseCodeEnum.INVALID_VEHICLE_LICENSE_PLATE);

    if (!vehicleRepo.existsByLicensePlate(dto.licensePlate()))
      throwException(MotoriZenResponseCodeEnum.VEHICLE_LICENSE_PLATE_ALREADY_EXISTS);

    if (dto.fuelCapacity().compareTo(BigDecimal.ZERO) <= 0)
      throwException(MotoriZenResponseCodeEnum.INVALID_VEHICLE_FUEL_CAPACITY);

    if (dto.odometer().compareTo(BigDecimal.ZERO) <= 0)
      throwException(MotoriZenResponseCodeEnum.INVALID_VEHICLE_ODOMETER);
  }

  public void validateVehicleUpdates(VehicleUpdatesDTO dto, UUID vehicleId) {

    if (dto.brandId() != null)
      if (!brandRepo.existsById(dto.brandId()))
        throwException(MotoriZenResponseCodeEnum.BRAND_NOT_FOUND);

    if (dto.fuelTypeId() != null)
      if (!fuelTypeRepo.existsById(dto.fuelTypeId()))
        throwException(MotoriZenResponseCodeEnum.FUEL_TYPE_NOT_FOUND);

    if (dto.model() != null)
      if (!isValidLength(dto.model(), 2, 100))
        throwException(MotoriZenResponseCodeEnum.INVALID_VEHICLE_MODEL);

    if (dto.renavam() != null) {
      if (!isValidLength(dto.renavam(), 11, 11))
        throwException(MotoriZenResponseCodeEnum.INVALID_VEHICLE_RENAVAM);

      if (vehicleRepo.existsByRenavamAndIdNot(dto.renavam(), vehicleId))
        throwException(MotoriZenResponseCodeEnum.VEHICLE_RENAVAM_ALREADY_EXISTS);
    }

    if (dto.year() != null)
      if ((dto.year() < 1900 || dto.year() > LocalDate.now().plusYears(1).getYear()))
        throwException(MotoriZenResponseCodeEnum.INVALID_VEHICLE_YEAR);

    if (dto.color() != null)
      if (!isValidLength(dto.color(), 3, 25))
        throwException(MotoriZenResponseCodeEnum.INVALID_VEHICLE_COLOR);

    if (dto.licensePlate() != null) {
      if (!isValidLength(dto.licensePlate(), 7, 10))
        throwException(MotoriZenResponseCodeEnum.INVALID_VEHICLE_LICENSE_PLATE);

      if (vehicleRepo.existsByLicensePlateAndIdNot(dto.licensePlate(), vehicleId))
        throwException(MotoriZenResponseCodeEnum.VEHICLE_LICENSE_PLATE_ALREADY_EXISTS);
    }

    if (dto.fuelCapacity() != null)
      if (dto.fuelCapacity().compareTo(BigDecimal.ZERO) <= 0)
        throwException(MotoriZenResponseCodeEnum.INVALID_VEHICLE_FUEL_CAPACITY);

    if (dto.odometer() != null)
      if (dto.odometer().compareTo(BigDecimal.ZERO) <= 0)
        throwException(MotoriZenResponseCodeEnum.INVALID_VEHICLE_ODOMETER);
  }
}
