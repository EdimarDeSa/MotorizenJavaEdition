package com.efscode.motorizen_backend.controllers;

import java.util.UUID;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.efscode.motorizen_backend.enums.MotoriZenResponseCodeEnum;
import com.efscode.motorizen_backend.models.dtos.ApiResponseBody;
import com.efscode.motorizen_backend.models.dtos.UserDTO;

@RestController
@RequiredArgsConstructor
public class UserController {

  @GetMapping("/user/me/{id}")
  public ResponseEntity<ApiResponseBody<UserDTO>> getMe(@RequestParam UUID id) {
    UserDTO userDTO = UserDTO.builder().id(id).build();

    ApiResponseBody<UserDTO> content = new ApiResponseBody<>(
        MotoriZenResponseCodeEnum.OK,
        userDTO);

    return ResponseEntity.status(HttpStatus.OK).body(content);
  }

}
