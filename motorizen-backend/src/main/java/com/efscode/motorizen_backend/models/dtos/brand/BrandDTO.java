package com.efscode.motorizen_backend.models.dtos.brand;

import lombok.Builder;

@Builder
public record BrandDTO(
    Integer id,
    String name,
    String createdAt,
    String updatedAt,
    String deletedAt) {
}
