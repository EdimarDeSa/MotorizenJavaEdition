package com.efscode.motorizen_backend.models.dtos;

import com.efscode.motorizen_backend.interfaces.DTOInterface;

import lombok.Builder;

@Builder
public record BrandDTO(
    Integer id,
    String name) implements DTOInterface {

  @Override
  public void validate() {
    if (name.length() > 50) {
      throw new IllegalArgumentException("name is too long");
    }
  }
}
