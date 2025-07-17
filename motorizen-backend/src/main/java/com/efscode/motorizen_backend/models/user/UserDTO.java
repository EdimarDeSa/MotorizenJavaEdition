package com.efscode.motorizen_backend.models.user;

import java.time.LocalDate;
import java.util.UUID;

import com.auth0.jwt.interfaces.DecodedJWT;

import lombok.Builder;

@Builder
public record UserDTO(
    UUID id,
    String firstName,
    String lastName,
    String email,
    LocalDate birthdate,
    Boolean isActive,
    Boolean isAdministrator) {

  public String getFullName() {
    return firstName + " " + lastName;
  }

  public String getInitials() {
    return (firstName.substring(0, 1)
        + lastName.substring(0, 1))
        .toUpperCase();
  }

  public Integer getAge() {
    int thisYearAge = LocalDate.now().getYear() - birthdate.getYear();
    if (LocalDate.now().getMonthValue() < birthdate.getMonthValue() ||
        LocalDate.now().getDayOfMonth() < birthdate.getDayOfMonth()) {
      thisYearAge--;
    }
    return thisYearAge;
  }

  public static UserDTO fromDecodedToken(DecodedJWT decodedJWT) {
    return UserDTO.builder()
        .id(UUID.fromString(decodedJWT.getSubject()))
        .firstName(decodedJWT.getClaim("fistName").asString())
        .lastName(decodedJWT.getClaim("lastName").asString())
        .email(decodedJWT.getClaim("email").asString())
        .birthdate(LocalDate.parse(decodedJWT.getClaim("birthdate").toString()))
        .isActive(decodedJWT.getClaim("isActive").asBoolean())
        .isAdministrator(decodedJWT.getClaim("isAdministrator").asBoolean())
        .build();
  }

}
