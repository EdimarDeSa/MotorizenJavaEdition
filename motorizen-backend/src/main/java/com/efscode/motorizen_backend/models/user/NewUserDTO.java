package com.efscode.motorizen_backend.models.user;

import java.time.LocalDate;

public record NewUserDTO(
    String firstName,
    String lastName,
    String email,
    String password,
    LocalDate birthdate) {
}
