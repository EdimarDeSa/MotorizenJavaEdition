package com.efscode.motorizen_backend.models.dtos;

import com.efscode.motorizen_backend.interfaces.DTOInterface;

import lombok.Builder;

@Builder
public record FuelTypeDTO(
    Integer id,
    String name) implements DTOInterface {

  @Override
  public void validate() {
    if (name.length() > 20) {
      throw new IllegalArgumentException("name is too long");
    }
  }

}
