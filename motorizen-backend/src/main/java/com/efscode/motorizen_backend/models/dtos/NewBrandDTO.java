package com.efscode.motorizen_backend.models.dtos;

public record NewBrandDTO(String name) {
  public BrandDTO toDTO() {
    return BrandDTO.builder()
        .name(name)
        .build();
  }
}
