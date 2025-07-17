package com.efscode.motorizen_backend.errors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.efscode.motorizen_backend.enums.MotoriZenResponseCodeEnum;
import com.efscode.motorizen_backend.models.ApiResponseBody;

import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
  @ExceptionHandler(MotorizenException.class)
  public ResponseEntity<ApiResponseBody<String>> handleMotorizenException(MotorizenException e) {
    log.debug("MotorizenException: " + e.getMessage());

    ApiResponseBody<String> response = new ApiResponseBody<>(e.getRc(), e.getMessage());

    return ResponseEntity.status(e.getHttpStatus()).body(response);
  }

  @ExceptionHandler(AuthorizationDeniedException.class)
  public ResponseEntity<ApiResponseBody<String>> handleAuthorizationDeniedException(
      AuthorizationDeniedException e) {
    log.debug("AuthorizationDeniedException: " + e.getMessage());

    MotoriZenResponseCodeEnum rc = MotoriZenResponseCodeEnum.UNAUTHORIZED_ACCESS;
    ApiResponseBody<String> response = new ApiResponseBody<>(rc, rc.getMessage());

    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ApiResponseBody<String>> handleException(Exception e) {
    log.error("Exception: " + e.getMessage());

    MotoriZenResponseCodeEnum rc = MotoriZenResponseCodeEnum.UNKNOWN_ERROR;
    ApiResponseBody<String> response = new ApiResponseBody<>(rc, rc.getMessage());

    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
  }
}
