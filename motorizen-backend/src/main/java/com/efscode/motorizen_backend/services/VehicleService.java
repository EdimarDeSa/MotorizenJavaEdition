package com.efscode.motorizen_backend.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.efscode.motorizen_backend.enums.MotoriZenResponseCodeEnum;
import com.efscode.motorizen_backend.errors.MotorizenException;
import com.efscode.motorizen_backend.models.brand.BrandEntity;
import com.efscode.motorizen_backend.models.fuel_type.FuelTypeEntity;
import com.efscode.motorizen_backend.models.user.UserEntity;
import com.efscode.motorizen_backend.models.vehicle.NewVehicleDTO;
import com.efscode.motorizen_backend.models.vehicle.VehicleDTO;
import com.efscode.motorizen_backend.models.vehicle.VehicleEntity;
import com.efscode.motorizen_backend.models.vehicle.VehicleFilterDTO;
import com.efscode.motorizen_backend.models.vehicle.VehicleMapper;
import com.efscode.motorizen_backend.models.vehicle.VehicleSpecifications;
import com.efscode.motorizen_backend.models.vehicle.VehicleUpdatesDTO;
import com.efscode.motorizen_backend.repositories.VehicleRepository;
import com.efscode.motorizen_backend.utils.validators.VehicleValidator;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class VehicleService {
  private final VehicleRepository vehicleRepo;
  private final VehicleMapper vehicleMapper;
  private final VehicleValidator validator;

  private final UserService userService;
  private final BrandService brandService;
  private final FuelTypeService fuelTypeService;

  public List<VehicleDTO> findAllVehicles(UUID userId) {
    List<VehicleEntity> vehicles = vehicleRepo.findAllByUserId(userId);

    if (vehicles.isEmpty())
      throw new MotorizenException(MotoriZenResponseCodeEnum.VEHICLE_NOT_FOUND);

    return vehicleMapper.entitiesToDtos(vehicles);
  }

  public VehicleDTO findVehicleById(UUID vehicleId, UUID userId) {
    VehicleEntity vehicle = vehicleRepo.findByIdAndUserId(vehicleId, userId);

    if (vehicle == null)
      throw new MotorizenException(MotoriZenResponseCodeEnum.VEHICLE_NOT_FOUND);

    return vehicleMapper.entityToDto(vehicle);
  }

  public List<VehicleDTO> filterVehicles(VehicleFilterDTO filter, UUID userId) {
    validator.validateFilter(filter);
    
    Specification<VehicleEntity> spec = VehicleSpecifications.mountFilterSpecification(filter, userId);
    List<VehicleEntity> vehicles = vehicleRepo.findAll(spec);

    if (vehicles.isEmpty())
      throw new MotorizenException(MotoriZenResponseCodeEnum.VEHICLE_NOT_FOUND);

    return vehicleMapper.entitiesToDtos(vehicles);
  }

  @Transactional
  public VehicleDTO createVehicle(NewVehicleDTO newVehicle, UUID userId) {
    log.debug("Iniciando createVehicle para o veículo`{}` do usuário`{}`", newVehicle.model(), userId);
    validator.validateNewVehicle(newVehicle);

    UserEntity user = userService.selectUserById(userId);
    BrandEntity brand = brandService.selectBrandById(newVehicle.brandId());
    FuelTypeEntity fuelType = fuelTypeService.selectFuelTypeById(newVehicle.fuelTypeId());

    VehicleEntity vehicle = vehicleMapper.newDtoToEntity(newVehicle, user, brand, fuelType);

    vehicleRepo.save(vehicle);

    return vehicleMapper.entityToDto(vehicle);
  }

  @Transactional
  public VehicleDTO updateVehicle(UUID vehicleId, VehicleUpdatesDTO vehicleUpdates, UUID userId) {
    log.debug("Iniciando updateVehicle para o id`{}` do usuário`{}`", vehicleId, userId);

    VehicleEntity vehicle = vehicleRepo.findByIdAndUserId(vehicleId, userId);

    if (vehicle == null)
      throw new MotorizenException(MotoriZenResponseCodeEnum.VEHICLE_NOT_FOUND);

    validator.validateVehicleUpdates(vehicleUpdates, vehicleId);

    if (vehicleUpdates.brandId() != null &&
        !vehicle.getBrand().getId().equals(vehicleUpdates.brandId())) {
      BrandEntity brand = brandService.selectBrandById(vehicleUpdates.brandId());
      vehicle.setBrand(brand);
    }

    if (vehicleUpdates.fuelTypeId() != null &&
        !vehicle.getFuelType().getId().equals(vehicleUpdates.fuelTypeId())) {
      FuelTypeEntity fuelType = fuelTypeService.selectFuelTypeById(vehicleUpdates.fuelTypeId());
      vehicle.setFuelType(fuelType);
    }

    vehicleMapper.updateEntityFromDto(vehicleUpdates, vehicle);
    vehicleRepo.save(vehicle);
    VehicleEntity updatedVehicle = vehicleRepo.findByIdAndUserId(vehicleId, userId);

    return vehicleMapper.entityToDto(updatedVehicle);
  }

  @Transactional
  public void deleteVehicle(UUID vehicleId, UUID userId) {
    log.debug("Iniciando deleteVehicle para o id`{}` do usuário`{}`", vehicleId, userId);

    VehicleEntity vehicle = vehicleRepo.findByIdAndUserIdAndDeletedAtIsNull(vehicleId, userId);

    if (vehicle == null)
      throw new MotorizenException(MotoriZenResponseCodeEnum.VEHICLE_NOT_FOUND);

    vehicle.setDeletedAt(LocalDateTime.now());

    vehicleRepo.save(vehicle);
  }
}
