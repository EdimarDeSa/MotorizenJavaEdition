package com.efscode.motorizen_backend.models.auth;

public record TokenDTO(
  String token,
  String refreshToken,
  String expiresIn,
  String refreshTokenExpiresIn
) {

}
