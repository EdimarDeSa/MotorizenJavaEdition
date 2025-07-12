package com.efscode.motorizen_backend.models.dtos;

import java.time.LocalDate;
import java.util.UUID;

import com.efscode.motorizen_backend.interfaces.DTOInterface;

import lombok.Builder;

@Builder
public record UserDTO(
    UUID id,
    String firstName,
    String lastName,
    String email,
    LocalDate birthdate,
    Boolean isActive,
    Boolean isAdministrator) implements DTOInterface {

  public String getFullName() {
    return firstName + " " + lastName;
  }

  public String getInitials() {
    return firstName.substring(0, 1) + lastName.substring(0, 1);
  }

  public Integer getAge() {
    int thisYearAge = LocalDate.now().getYear() - birthdate.getYear();
    if (LocalDate.now().getMonthValue() < birthdate.getMonthValue() ||
        LocalDate.now().getDayOfMonth() < birthdate.getDayOfMonth()) {
      thisYearAge--;
    }
    return thisYearAge;
  }

  @Override
  public void validate() {
    if (firstName.length() > 50) {
      throw new IllegalArgumentException("firstName is too long");
    }

    if (lastName.length() > 100) {
      throw new IllegalArgumentException("lastName is too long");
    }

    if (email.length() > 255) {
      throw new IllegalArgumentException("email is too long");
    }

    if (!email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")) {
      throw new IllegalArgumentException("Invalid email format");
    }

    if (getAge() < 18) {
      throw new IllegalArgumentException("User must be at least 18 years old");
    }
  }

}
