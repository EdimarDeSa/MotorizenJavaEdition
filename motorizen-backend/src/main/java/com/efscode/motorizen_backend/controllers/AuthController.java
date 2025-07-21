package com.efscode.motorizen_backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.efscode.motorizen_backend.models.ApiResponseBody;
import com.efscode.motorizen_backend.models.auth.LoginDTO;
import com.efscode.motorizen_backend.models.auth.TokenDTO;
import com.efscode.motorizen_backend.services.AuthService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/auth")
@Tag(name = "Auth", description = "Auth API")
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

  @GetMapping("/renew-token/{refreshToken}")
  public ResponseEntity<ApiResponseBody<TokenDTO>> renewToken(@PathVariable String refreshToken) {
    log.debug("Iniciando renewToken");

    TokenDTO token = authService.renewToken(refreshToken);
    ApiResponseBody<TokenDTO> content = ApiResponseBody.ok(token);

    return ResponseEntity.ok(content);
  }

  @GetMapping("/logout")
  public ResponseEntity<ApiResponseBody<Void>> logout() {
    // TODO: Criar limpeza dos caches
    log.debug("Iniciando logout");

    authService.logout();
    ApiResponseBody<Void> response = ApiResponseBody.ok(null);

    return ResponseEntity.ok(response);
  }

}
