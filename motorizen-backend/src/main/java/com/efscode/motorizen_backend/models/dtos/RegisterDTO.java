package com.efscode.motorizen_backend.models.dtos;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

import com.efscode.motorizen_backend.interfaces.DTOInterface;

import lombok.Builder;

@Builder
public record RegisterDTO(
    UUID id,
    UserDTO user,
    VehicleDTO vehicle,
    LocalTime workTime,
    LocalDate registerDate,
    Double distance,
    Double meanConsuption,
    Integer numberOfTrips,
    BigDecimal value) implements DTOInterface {

  @Override
  public void validate() {
    if (distance < 0) {
      throw new IllegalArgumentException("distance cannot be negative");
    }

    if (meanConsuption < 0) {
      throw new IllegalArgumentException("meanConsuption cannot be negative");
    }

    if (numberOfTrips < 0) {
      throw new IllegalArgumentException("numberOfTrips cannot be negative");
    }

    if (value.compareTo(BigDecimal.ZERO) < 0) {
      throw new IllegalArgumentException("value cannot be negative");
    }

    if (workTime.isAfter(LocalTime.of(23, 59))) {
      throw new IllegalArgumentException("workTime cannot be greater than 23:59");
    }

    if (registerDate.isAfter(LocalDate.now())) {
      throw new IllegalArgumentException("registerDate cannot be in the future");
    }

    if (registerDate.isBefore(LocalDate.of(1900, 1, 1))) {
      throw new IllegalArgumentException("registerDate cannot be before 1900-01-01");
    }

    if (user == null) {
      throw new IllegalArgumentException("user cannot be null");
    }

    if (vehicle == null) {
      throw new IllegalArgumentException("vehicle cannot be null");
    }

    if (workTime == null) {
      throw new IllegalArgumentException("workTime cannot be null");
    }

    if (registerDate == null) {
      throw new IllegalArgumentException("registerDate cannot be null");
    }

    if (distance == null) {
      throw new IllegalArgumentException("distance cannot be null");
    }

    if (meanConsuption == null) {
      throw new IllegalArgumentException("meanConsuption cannot be null");
    }

    if (numberOfTrips == null) {
      throw new IllegalArgumentException("numberOfTrips cannot be null");
    }

    if (value == null) {
      throw new IllegalArgumentException("value cannot be null");
    }

    if (user == null) {
      return;
    }

    user.validate();

    if (vehicle == null) {
      return;
    }

    vehicle.validate();

    if (user.id() != vehicle.user().id()) {
      throw new IllegalArgumentException("user and vehicle must be from the same user");
    }
  }
}
