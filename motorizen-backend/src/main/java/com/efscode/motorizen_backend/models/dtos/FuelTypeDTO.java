package com.efscode.motorizen_backend.models.dtos;

import lombok.Builder;

@Builder
public record FuelTypeDTO(
    Integer id,
    String name) {
}
