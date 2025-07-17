package com.efscode.motorizen_backend.models.brand;

import lombok.Builder;

@Builder
public record BrandDTO(
    Integer id,
    String name,
    String createdAt,
    String updatedAt,
    String deletedAt) {
}
