package com.efscode.motorizen_backend.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.efscode.motorizen_backend.models.dtos.ApiResponseBody;
import com.efscode.motorizen_backend.models.dtos.LoginDTO;
import com.efscode.motorizen_backend.models.dtos.TokenDTO;
import com.efscode.motorizen_backend.services.AuthService;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/auth")
@Slf4j
public class AuthController {
  @Autowired
  private AuthService authService;

  @PostMapping("/login")
  public ResponseEntity<ApiResponseBody<TokenDTO>> login(@RequestBody @Valid LoginDTO loginDTO) {
    log.debug("Iniciando login para o usuário`{}`", loginDTO.login());

    TokenDTO token = authService.login(loginDTO);
    ApiResponseBody<TokenDTO> content = ApiResponseBody.ok(token);

    return ResponseEntity.ok(content);
  }

  @GetMapping("/logout")
  public ResponseEntity<ApiResponseBody<Void>> logout(@RequestHeader(name = "Authorization") String token) {
    ApiResponseBody<Void> response = ApiResponseBody.ok(null);
    return ResponseEntity.ok(response);
  }

}
