package com.efscode.motorizen_backend.models.user;

import java.time.LocalDate;

public record UserUpdatesDTO(
    String firstName,
    String lastName,
    LocalDate birthdate
) {
}
