package com.efscode.motorizen_backend.enums;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum MotoriZenResponseCodeEnum {
  // OK
  OK(0, HttpStatus.OK, "OK"),

  // Erros de usuário -1xx
  USER_NOT_ACTIVE(-100, HttpStatus.UNAUTHORIZED, "User not active."),
  USER_NOT_FOUND(-104, HttpStatus.NOT_FOUND, "User not found."),
  USER_ALREADY_REGISTERED(-105, HttpStatus.CONFLICT, "User already registered."),
  USER_EMAIL_ALREADY_REGISTERED(-106, HttpStatus.CONFLICT, "User email already registered."),
  USER_PHONE_ALREADY_REGISTERED(-107, HttpStatus.CONFLICT, "User phone already registered."),
  USER_DOCUMENT_ALREADY_REGISTERED(-108, HttpStatus.CONFLICT, "User document already registered."),
  USER_ALREADY_EXISTS(-109, HttpStatus.CONFLICT, "User already exists."),
  TOKEN_EXPIRED(-110, HttpStatus.UNAUTHORIZED, "Token expired."),
  TOKEN_INVALID(-111, HttpStatus.UNAUTHORIZED, "Token invalid"),
  MISSING_CSRF_TOKEN(-400, HttpStatus.FORBIDDEN, "Missing CSRF token."),
  INVALID_CSRF_TOKEN(-401, HttpStatus.FORBIDDEN, "Invalid CSRF token."),
  INVALID_USER_NAME(-402, HttpStatus.BAD_REQUEST, "Invalid user name."),
  UNAUTHORIZED_ACCESS(-403, HttpStatus.FORBIDDEN, "Unauthorized access."),


  // Erros de login -2xx
  LOGIN_ERROR(-200, HttpStatus.UNAUTHORIZED, "Login error."),
  LOGOUT_ERROR(-201, HttpStatus.UNAUTHORIZED, "Logout error."),
  INVALID_CREDENTIALS(-202, HttpStatus.UNAUTHORIZED, "Invalid credentials."),
  INVALID_REFRESH_TOKEN(-203, HttpStatus.UNAUTHORIZED, "Invalid refresh token."),

  // Erros de veículos -3xx
  VEHICLE_NOT_FOUND(-300, HttpStatus.NOT_FOUND, "Vehicle not found."),
  BRAND_NOT_FOUND(-301, HttpStatus.NOT_FOUND, "Brand not found."),
  REGISTER_NOT_FOUND(-302, HttpStatus.NOT_FOUND, "Register not found."),
  FUEL_TYPE_NOT_FOUND(-303, HttpStatus.NOT_FOUND, "Fuel type not found."),
  INVALID_UPDATES_DATA(-310, HttpStatus.BAD_REQUEST, "Invalid updates data."),
  INVALID_REGISTER_DATE(-311, HttpStatus.BAD_REQUEST, "Invalid register date."),
  INVALID_PASSWORD(-312, HttpStatus.BAD_REQUEST, "Invalid password."),
  INVALID_BRAND_NAME(-313, HttpStatus.BAD_REQUEST, "Invalid brand name."),
  INVALID_USER_EMAIL(-314, HttpStatus.BAD_REQUEST, "Invalid user email."),
  USER_MUST_BE_18(-315, HttpStatus.BAD_REQUEST, "User must be at least 18 years old."),
  BRAND_ALREADY_EXISTS(-316, HttpStatus.CONFLICT, "Brand already exists."),
  INVALID_FUEL_TYPE_NAME(-317, HttpStatus.BAD_REQUEST, "Invalid fuel type name."),
  FUEL_TYPE_ALREADY_EXISTS(-318, HttpStatus.CONFLICT, "Fuel type already exists."),

  // Erros de relatórios -4xx
  INVALID_SORT_KEY(-800, HttpStatus.BAD_REQUEST, "Invalid sort key."),

  // Erros internos -9xx
  CONFIG_FILE_NOT_FOUND(-900, HttpStatus.INTERNAL_SERVER_ERROR, "Config file not found."),
  UNKNOWN_ERROR(-999, HttpStatus.INTERNAL_SERVER_ERROR, "Não foi possível processar a requisição. Tente novamente mais tarde ou entre em contato com o suporte.");

  private final Integer responseCode;
  private final HttpStatus httpStatus;
  private final String message;
}
