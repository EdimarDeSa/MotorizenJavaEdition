package com.efscode.motorizen_backend.models.fuel_type;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface FuelTypeMapper {
  FuelTypeMapper INSTANCE = Mappers.getMapper(FuelTypeMapper.class);

  FuelTypeEntity newDtoToEntity(NewFuelTypeDTO dto);

  @Mapping(target = "createdAt", dateFormat = "dd-MM-yyyy HH:mm:ss")
  @Mapping(target = "updatedAt", dateFormat = "dd-MM-yyyy HH:mm:ss")
  @Mapping(target = "deletedAt", dateFormat = "dd-MM-yyyy HH:mm:ss")
  FuelTypeDTO entityToDto(FuelTypeEntity entity);

  @Mapping(target = "createdAt", dateFormat = "dd-MM-yyyy HH:mm:ss")
  @Mapping(target = "updatedAt", dateFormat = "dd-MM-yyyy HH:mm:ss")
  @Mapping(target = "deletedAt", dateFormat = "dd-MM-yyyy HH:mm:ss")
  List<FuelTypeDTO> entitiesToDtos(List<FuelTypeEntity> entities);

  void updateEntityFromDto(FuelTypeUpdatesDTO dto, @MappingTarget FuelTypeEntity entity);
}
