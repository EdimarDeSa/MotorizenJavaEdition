package com.efscode.motorizen_backend.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.efscode.motorizen_backend.enums.MotoriZenResponseCodeEnum;
import com.efscode.motorizen_backend.models.dtos.ApiResponseBody;
import com.efscode.motorizen_backend.models.dtos.NewUser;
import com.efscode.motorizen_backend.models.dtos.UserDTO;
import com.efscode.motorizen_backend.services.UserService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@Slf4j
public class UserController {
  private final UserService userService;

  @GetMapping("/me")
  @SecurityRequirement(name = "bearerAuth")
  public ResponseEntity<ApiResponseBody<UserDTO>> getMe(
      @AuthenticationPrincipal UserDTO userDTO) {

    log.debug("Iniciando getMe para o usuário`{}`", userDTO);

    ApiResponseBody<UserDTO> content = new ApiResponseBody<>(
        MotoriZenResponseCodeEnum.OK,
        userDTO);

    return ResponseEntity.status(HttpStatus.OK).body(content);
  }

  @PostMapping("/new")
  public ResponseEntity<ApiResponseBody<Void>> createNewUser(@RequestBody NewUser newUser) {
    log.debug("Iniciando createNewUser para o usuário`{}`", newUser.getEmail());
    userService.createNewUser(newUser);

    ApiResponseBody<Void> content = new ApiResponseBody<>(
        MotoriZenResponseCodeEnum.OK, null);

    return ResponseEntity.status(HttpStatus.CREATED).body(content);
  }

}
