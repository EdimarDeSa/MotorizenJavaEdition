package com.efscode.motorizen_backend.models.user;

import java.time.LocalDate;

import lombok.Getter;
import lombok.ToString;

@Getter
public class NewUser {
  private String firstName;
  private String lastName;
  private String email;

  @ToString.Exclude
  private String password;
  private LocalDate birthdate;

  public NewUser(String firstName, String lastName, String email, String password, LocalDate birthdate) {
    this.firstName = firstName.toLowerCase();
    this.lastName = lastName.toLowerCase();
    this.email = email.toLowerCase();
    this.password = password;
    this.birthdate = birthdate;
  }

  public UserDTO toUserDTO() {
    return UserDTO.builder()
        .firstName(firstName)
        .lastName(lastName)
        .email(email)
        .birthdate(birthdate)
        .build();
  }
}
