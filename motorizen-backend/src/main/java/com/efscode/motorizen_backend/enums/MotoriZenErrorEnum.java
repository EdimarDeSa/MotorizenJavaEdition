package com.efscode.motorizen_backend.enums;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum MotoriZenErrorEnum {
  USER_NOT_ACTIVE(HttpStatus.UNAUTHORIZED, -100),
  USER_NOT_FOUND(HttpStatus.NOT_FOUND, -104),
  USER_ALREADY_EXISTS(HttpStatus.CONFLICT, -109),
  TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, -110),

  LOGIN_ERROR(HttpStatus.UNAUTHORIZED, -200),
  LOGOUT_ERROR(HttpStatus.UNAUTHORIZED, -201),
  INVALID_CREDENTIALS(HttpStatus.UNAUTHORIZED, -202),

  VEHICLE_NOT_FOUND(HttpStatus.NOT_FOUND, -300),
  BRAND_NOT_FOUND(HttpStatus.NOT_FOUND, -301),
  REGISTER_NOT_FOUND(HttpStatus.NOT_FOUND, -302),
  FUEL_TYPE_NOT_FOUND(HttpStatus.NOT_FOUND, -303),

  INVALID_UPDATES_DATA(HttpStatus.BAD_REQUEST, -310),
  INVALID_REGISTER_DATE(HttpStatus.BAD_REQUEST, -311),
  INVALID_PASSWORD(HttpStatus.BAD_REQUEST, -312),

  MISSING_CSRF_TOKEN(HttpStatus.BAD_REQUEST, -400),
  INVALID_CSRF_TOKEN(HttpStatus.BAD_REQUEST, -401),

  INVALID_SORT_KEY(HttpStatus.BAD_REQUEST, -800),

  CONFIG_FILE_NOT_FOUND(HttpStatus.INTERNAL_SERVER_ERROR, -900),
  UNKNOWN_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, -999);

  private final HttpStatus statusCode;
  private final int errorCode;
}
