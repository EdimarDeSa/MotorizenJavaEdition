package com.efscode.motorizen_backend.models.dtos;

import lombok.Builder;

@Builder
public record BrandDTO(
    Integer id,
    String name) {
}
