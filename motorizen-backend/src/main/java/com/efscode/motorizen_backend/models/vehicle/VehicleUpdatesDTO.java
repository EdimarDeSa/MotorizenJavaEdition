package com.efscode.motorizen_backend.models.vehicle;

import java.math.BigDecimal;

public record VehicleUpdatesDTO(
    Integer brandId,
    Integer fuelTypeId,
    String model,
    String renavam,
    Integer year,
    String color,
    String licensePlate,
    BigDecimal fuelCapacity,
    BigDecimal odometer) {

}
