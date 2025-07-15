package com.efscode.motorizen_backend.models.dtos;

import lombok.Builder;

@Builder
public record FuelTypeDTO(
    Integer id,
    String name) {

  public void validate() {
    if (name.length() > 20) {
      throw new IllegalArgumentException("name is too long");
    }
  }

}
