package com.efscode.motorizen_backend.models;

import com.efscode.motorizen_backend.enums.MotoriZenResponseCodeEnum;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ApiResponseBody<T> {
    private Integer rc;
    private T data;

    public ApiResponseBody(MotoriZenResponseCodeEnum rc, T data) {
        this.rc = rc.getResponseCode();
        this.data = data;
    }

    public static <T> ApiResponseBody<T> ok(T data) {
        return new ApiResponseBody<T>(MotoriZenResponseCodeEnum.OK, data);
    }
}
