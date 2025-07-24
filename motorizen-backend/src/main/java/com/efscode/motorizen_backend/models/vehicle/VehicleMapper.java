package com.efscode.motorizen_backend.models.vehicle;

import java.util.List;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import com.efscode.motorizen_backend.models.brand.BrandEntity;
import com.efscode.motorizen_backend.models.fuel_type.FuelTypeEntity;
import com.efscode.motorizen_backend.models.user.UserEntity;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
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
  @Mapping(target = "isActive", constant = "true")
  @Mapping(target = "createdAt", ignore = true)
  @Mapping(target = "updatedAt", ignore = true)
  @Mapping(target = "deletedAt", ignore = true)
  VehicleEntity newDtoToEntity(
      NewVehicleDTO dto,
      UserEntity user,
      BrandEntity brand,
      FuelTypeEntity fuelType);

  @Mapping(target = "brand", ignore = true)
  @Mapping(target = "fuelType", ignore = true)
  void updateEntityFromDto(VehicleUpdatesDTO dto, @MappingTarget VehicleEntity entity);

  @AfterMapping
  default void formatLicensePlate(@MappingTarget VehicleEntity entity) {
    if (entity.getLicensePlate() != null &&
        !entity.getLicensePlate().isEmpty() &&
        !entity.getLicensePlate().contains("-")) {
      String formattedLicensePlate = entity.getLicensePlate().substring(0, 3)
          + "-"
          + entity.getLicensePlate().substring(3);
      entity.setLicensePlate(formattedLicensePlate);
    }
  }
}
