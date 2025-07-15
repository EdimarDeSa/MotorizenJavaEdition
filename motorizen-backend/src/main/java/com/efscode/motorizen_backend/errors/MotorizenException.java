package com.efscode.motorizen_backend.errors;

import lombok.Getter;

import org.springframework.http.HttpStatus;

import com.efscode.motorizen_backend.enums.MotoriZenResponseCodeEnum;

@Getter
public class MotorizenException extends RuntimeException {
  private Integer rc;
  private HttpStatus httpStatus;
  private String message;

  public MotorizenException(MotoriZenResponseCodeEnum rc) {
    super(rc.getMessage());
    this.rc = rc.getResponseCode();
    this.httpStatus = rc.getHttpStatus();
    this.message = rc.getMessage();
  }
}
