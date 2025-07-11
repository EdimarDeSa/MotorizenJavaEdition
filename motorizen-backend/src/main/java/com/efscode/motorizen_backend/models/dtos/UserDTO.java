package com.efscode.motorizen_backend.models.dtos;

import java.time.LocalDate;
import java.util.UUID;

import lombok.Builder;

@Builder
public record UserDTO(
    UUID id,
    String firstName,
    String lastName,
    String email,
    LocalDate birthdate,
    Boolean isActive) {

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

}
