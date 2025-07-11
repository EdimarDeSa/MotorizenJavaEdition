package com.efscode.motorizen_backend.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum MotoriZenResponseCodeEnum {
  // OK 
  OK(0),

  // Erros de usuário -1xx
  USER_NOT_ACTIVE(-100),
  USER_NOT_FOUND(-104),
  USER_ALREADY_EXISTS(-109),
  TOKEN_EXPIRED(-110),
  MISSING_CSRF_TOKEN(-400),
  INVALID_CSRF_TOKEN(-401),

  // Erros de login -2xx
  LOGIN_ERROR(-200),
  LOGOUT_ERROR(-201),
  INVALID_CREDENTIALS(-202),

  // Erros de veículos -3xx
  VEHICLE_NOT_FOUND(-300),
  BRAND_NOT_FOUND(-301),
  REGISTER_NOT_FOUND(-302),
  FUEL_TYPE_NOT_FOUND(-303),
  INVALID_UPDATES_DATA(-310),
  INVALID_REGISTER_DATE(-311),
  INVALID_PASSWORD(-312),

  // Erros de relatórios -4xx
  INVALID_SORT_KEY(-800),

  // Erros internos -9xx
  CONFIG_FILE_NOT_FOUND(-900),
  UNKNOWN_ERROR(-999);

  private final Integer responseCode;
}
