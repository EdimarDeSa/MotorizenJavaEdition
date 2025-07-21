package com.efscode.motorizen_backend.models.register;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

import com.efscode.motorizen_backend.models.user.UserDTO;
import com.efscode.motorizen_backend.models.vehicle.VehicleDTO;

import lombok.Builder;

@Builder
public record RegisterDTO(
    UUID id,
    UserDTO user,
    VehicleDTO vehicle,
    LocalTime workTime,
    LocalDate registerDate,
    BigDecimal distance,
    BigDecimal meanConsuption,
    Integer numberOfTrips,
    BigDecimal value) {

}
