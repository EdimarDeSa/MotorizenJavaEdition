package com.efscode.motorizen_backend.models.dtos;

import java.util.UUID;

import lombok.Builder;

@Builder
public record VehicleDTO(
  UUID id,
  UserDTO user,
  BrandDTO brand,
  FuelTypeDTO fuelType,
  String model,
  String renavam,
  String color,
  String licensePlate,
  Double fuelCapacity,
  Double odometer,
  Boolean isActive
) {

}
