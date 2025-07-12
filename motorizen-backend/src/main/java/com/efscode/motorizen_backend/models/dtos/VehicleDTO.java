package com.efscode.motorizen_backend.models.dtos;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.UUID;

import com.efscode.motorizen_backend.interfaces.DTOInterface;

import lombok.Builder;

@Builder
public record VehicleDTO(
  UUID id,
  UserDTO user,
  BrandDTO brand,
  FuelTypeDTO fuelType,
  String model,
  String renavam,
  Integer year,
  String color,
  String licensePlate,
  BigDecimal fuelCapacity,
  BigDecimal odometer,
  Boolean isActive
) implements DTOInterface {

  @Override
  public void validate() {
    if (model.length() > 100) {
      throw new IllegalArgumentException("model is too long");
    }

    if (renavam.length() > 11) {
      throw new IllegalArgumentException("renavam is too long");
    }

    if (color.length() > 25) {
      throw new IllegalArgumentException("color is too long");
    }

    if (licensePlate.length() > 10) {
      throw new IllegalArgumentException("licensePlate is too long");
    }

    if (fuelCapacity.compareTo(BigDecimal.ZERO) < 0) {
      throw new IllegalArgumentException("fuelCapacity cannot be negative");
    }

    if (odometer.compareTo(BigDecimal.ZERO) < 0) {
      throw new IllegalArgumentException("odometer cannot be negative");
    }

    if (isActive == null) {
      throw new IllegalArgumentException("isActive cannot be null");
    }

    if (year < 1900) {
      throw new IllegalArgumentException("year cannot be less than 1900");
    }

    if (year > Calendar.getInstance().get(Calendar.YEAR)) {
      throw new IllegalArgumentException("year cannot be greater than current year");
    }

    user.validate();
    brand.validate();
    fuelType.validate();
  }

}
