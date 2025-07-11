package com.efscode.motorizen_backend.models.dtos;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

import lombok.Builder;

@Builder
public record RegisterDTO(
    UUID id,
    UserDTO user,
    VehicleDTO vehicle,
    String licensePlate,
    Integer trips,
    Double distance,
    Double meanConsuption,
    BigDecimal value,
    LocalTime workTime,
    LocalDate date) {
}
