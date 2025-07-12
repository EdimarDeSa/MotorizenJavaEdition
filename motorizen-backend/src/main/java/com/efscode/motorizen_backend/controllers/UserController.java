package com.efscode.motorizen_backend.controllers;

import java.util.UUID;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.efscode.motorizen_backend.enums.MotoriZenResponseCodeEnum;
import com.efscode.motorizen_backend.models.dtos.ApiResponseBody;
import com.efscode.motorizen_backend.models.dtos.UserDTO;
import com.efscode.motorizen_backend.services.UserService;

@RestController
@RequiredArgsConstructor
@Slf4j
public class UserController {
  private final UserService userService;

  @GetMapping("/user/me/{user_id}")
  public ResponseEntity<ApiResponseBody<UserDTO>> getMe(@RequestParam UUID user_id) {
    log.debug("Iniciando getMe para o usuário`{}`", user_id);
    UserDTO userDTO = userService.getMe(user_id);

    ApiResponseBody<UserDTO> content = new ApiResponseBody<>(
        MotoriZenResponseCodeEnum.OK,
        userDTO);

    return ResponseEntity.status(HttpStatus.OK).body(content);
  }

}
