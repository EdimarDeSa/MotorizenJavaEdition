package com.efscode.motorizen_backend.models.vehicle;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import com.efscode.motorizen_backend.models.brand.BrandEntity;
import com.efscode.motorizen_backend.models.fuel_type.FuelTypeEntity;
import com.efscode.motorizen_backend.models.user.UserEntity;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface VehicleMapper {

  @Mapping(target = "userId", source = "user.id")
  @Mapping(target = "brandId", source = "brand.id")
  @Mapping(target = "fuelTypeId", source = "fuelType.id")
  @Mapping(target = "createdAt", dateFormat = "dd-MM-yyyy HH:mm:ss")
  @Mapping(target = "updatedAt", dateFormat = "dd-MM-yyyy HH:mm:ss")
  @Mapping(target = "deletedAt", dateFormat = "dd-MM-yyyy HH:mm:ss")
  List<VehicleDTO> entitiesToDtos(List<VehicleEntity> entities);

  @Mapping(target = "userId", source = "user.id")
  @Mapping(target = "brandId", source = "brand.id")
  @Mapping(target = "fuelTypeId", source = "fuelType.id")
  @Mapping(target = "createdAt", dateFormat = "dd-MM-yyyy HH:mm:ss")
  @Mapping(target = "updatedAt", dateFormat = "dd-MM-yyyy HH:mm:ss")
  @Mapping(target = "deletedAt", dateFormat = "dd-MM-yyyy HH:mm:ss")
  VehicleDTO entityToDto(VehicleEntity entity);

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "user", source = "user")
  @Mapping(target = "brand", source = "brand")
  @Mapping(target = "fuelType", source = "fuelType")
  @Mapping(target = "isActive", constant = "true")
  @Mapping(target = "createdAt", ignore = true)
  @Mapping(target = "updatedAt", ignore = true)
  @Mapping(target = "deletedAt", ignore = true)
  VehicleEntity newDtoToEntity(
      NewVehicleDTO dto,
      UserEntity user,
      BrandEntity brand,
      FuelTypeEntity fuelType);

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "user", ignore = true)
  @Mapping(target = "brand", ignore = true)
  @Mapping(target = "fuelType", ignore = true)
  @Mapping(target = "model", source = "dto.model")
  @Mapping(target = "renavam", source = "dto.renavam")
  @Mapping(target = "year", source = "dto.year")
  @Mapping(target = "color", source = "dto.color")
  @Mapping(target = "licensePlate", source = "dto.licensePlate")
  @Mapping(target = "fuelCapacity", source = "dto.fuelCapacity")
  @Mapping(target = "odometer", source = "dto.odometer")
  @Mapping(target = "isActive", ignore = true)
  @Mapping(target = "createdAt", ignore = true)
  @Mapping(target = "updatedAt", ignore = true)
  @Mapping(target = "deletedAt", ignore = true)
  VehicleEntity updateEntityFromDto(VehicleUpdatesDTO dto, VehicleEntity entity);
}
