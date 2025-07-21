package com.efscode.motorizen_backend.models.vehicle;

import java.util.UUID;

public record VehicleFilter(
    UUID id,
    Integer brandId,
    Integer fuelTypeId,
    String model,
    String renavam,
    Integer year,
    String color,
    String licensePlate,
    Boolean isActive,
    Boolean isDeleted) {

}
