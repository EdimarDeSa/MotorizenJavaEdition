package com.efscode.motorizen_backend.models.dtos;

import com.efscode.motorizen_backend.enums.MotoriZenResponseCodeEnum;

public record ApiResponseBody<T>(
    MotoriZenResponseCodeEnum responseCode,
    T data) {
}
