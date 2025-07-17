package com.efscode.motorizen_backend.models.user;

import java.time.LocalDate;

public record NewUser(
    String firstName,
    String lastName,
    String email,
    String password,
    LocalDate birthdate) {
}
