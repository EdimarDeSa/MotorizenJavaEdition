package com.efscode.motorizen_backend.models.dtos;

public record NewFuelType(String name) {
  public FuelTypeDTO toDTO() {
    return FuelTypeDTO.builder()
        .name(name)
        .build();
  }
}
