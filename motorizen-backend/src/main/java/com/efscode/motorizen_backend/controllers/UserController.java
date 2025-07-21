package com.efscode.motorizen_backend.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.efscode.motorizen_backend.enums.MotoriZenResponseCodeEnum;
import com.efscode.motorizen_backend.models.ApiResponseBody;
import com.efscode.motorizen_backend.models.user.NewUserDTO;
import com.efscode.motorizen_backend.models.user.UserDTO;
import com.efscode.motorizen_backend.models.user.UserUpdateEmailDTO;
import com.efscode.motorizen_backend.models.user.UserUpdatePasswordDTO;
import com.efscode.motorizen_backend.models.user.UserUpdatesDTO;
import com.efscode.motorizen_backend.services.UserService;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@Tag(name = "Users", description = "Users API")
@Slf4j
public class UserController {
  private final UserService userService;

  @GetMapping("/me")
  public ResponseEntity<ApiResponseBody<UserDTO>> getMe(
      @AuthenticationPrincipal UserDTO logedUser) {
    log.debug("Iniciando getMe para o usuário`{}`", logedUser);

    ApiResponseBody<UserDTO> content = new ApiResponseBody<>(
        MotoriZenResponseCodeEnum.OK,
        logedUser);

    return ResponseEntity.status(HttpStatus.OK).body(content);
  }

  @PostMapping("/new")
  public ResponseEntity<ApiResponseBody<Void>> createNewUser(@RequestBody NewUserDTO newUser) {
    log.debug("Iniciando createNewUser para o usuário`{}`", newUser.email());

    userService.createNewUser(newUser);
    ApiResponseBody<Void> content = new ApiResponseBody<>(MotoriZenResponseCodeEnum.OK, null);

    return ResponseEntity.status(HttpStatus.CREATED).body(content);
  }

  @PatchMapping("update-data")
  public ResponseEntity<ApiResponseBody<UserDTO>> updateUser(@AuthenticationPrincipal UserDTO logedUser,
      @RequestBody UserUpdatesDTO userUpdates) {
    // TODO: atualizar cache dos dados do user
    log.debug("Iniciando updateUser para o usuário`{}`", logedUser.id());

    UserDTO user = userService.updateUser(logedUser.id(), userUpdates);
    ApiResponseBody<UserDTO> content = ApiResponseBody.ok(user);

    return ResponseEntity.ok(content);
  }

  @PatchMapping("update-email")
  public ResponseEntity<ApiResponseBody<UserDTO>> updateUserEmail(@AuthenticationPrincipal UserDTO logedUser,
      @RequestBody UserUpdateEmailDTO emailUpdate) {
    // TODO: atualizar cache dos dados do user
    log.debug("Iniciando updateUserEmail para o usuário`{}`", logedUser.id());

    UserDTO user = userService.updateUserEmail(logedUser.id(), emailUpdate);
    ApiResponseBody<UserDTO> content = ApiResponseBody.ok(user);

    return ResponseEntity.ok(content);
  }

  @PatchMapping("update-password")
  public ResponseEntity<ApiResponseBody<Void>> updateUserPassword(@AuthenticationPrincipal UserDTO logedUser,
      @RequestBody UserUpdatePasswordDTO passwordUpdate) {
    // TODO: atualizar cache dos dados do user
    log.debug("Iniciando updateUserPassword para o usuário`{}`");

    userService.updateUserPassword(logedUser.id(), passwordUpdate);
    ApiResponseBody<Void> content = ApiResponseBody.ok(null);

    return ResponseEntity.ok(content);
  }

  @DeleteMapping("/delete")
  public ResponseEntity<ApiResponseBody<Void>> deleteUser(@AuthenticationPrincipal UserDTO logedUser) {
    // TODO: atualizar cache de login
    log.debug("Iniciando deleteUser para o usuário`{}`", logedUser.id());

    userService.deleteUser(logedUser.id());
    ApiResponseBody<Void> content = ApiResponseBody.ok(null);

    return ResponseEntity.ok(content);
  }
}
