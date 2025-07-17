package com.efscode.motorizen_backend.models.fuel_type;

import lombok.Builder;

@Builder
public record FuelTypeDTO(
    Integer id,
    String name,
    String createdAt,
    String updatedAt,
    String deletedAt) {
}
