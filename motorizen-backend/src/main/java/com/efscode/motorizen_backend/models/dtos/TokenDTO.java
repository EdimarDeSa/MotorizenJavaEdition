package com.efscode.motorizen_backend.models.dtos;

public record TokenDTO(
  String token,
  String refreshToken,
  String expiresIn,
  String refreshTokenExpiresIn
) {

}
