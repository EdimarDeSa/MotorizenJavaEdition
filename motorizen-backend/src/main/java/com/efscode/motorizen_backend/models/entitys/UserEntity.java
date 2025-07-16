package com.efscode.motorizen_backend.models.entitys;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.efscode.motorizen_backend.interfaces.EntityInterface;
import com.efscode.motorizen_backend.models.dtos.NewUser;
import com.efscode.motorizen_backend.models.dtos.UserDTO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserEntity implements EntityInterface<UserDTO>, UserDetails {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @Column(length = 50, nullable = false)
  private String firstName;

  @Column(length = 100, nullable = false)
  private String lastName;

  @Column(length = 255, nullable = false, unique = true)
  private String email;

  @Column(length = 100, nullable = false)
  @ToString.Exclude
  private String password;

  @Column(nullable = true)
  private LocalDate birthdate;

  @Column(nullable = false)
  @Builder.Default
  private Boolean isActive = true;

  @Column(nullable = false)
  @Builder.Default
  private Boolean isAdministrator = false;

  @CreationTimestamp
  @Column(updatable = false)
  private LocalDateTime createdAt;

  @UpdateTimestamp
  private LocalDateTime updatedAt;

  @Column(nullable = true)
  private LocalDateTime deletedAt;

  @ToString.Exclude
  @EqualsAndHashCode.Exclude
  @OneToMany(mappedBy = "user")
  private Set<VehicleEntity> vehicles;

  public UserEntity(NewUser userDTO, String passwordHash) {
    this.firstName = userDTO.getFirstName();
    this.lastName = userDTO.getLastName();
    this.email = userDTO.getEmail();
    this.password = passwordHash;
    this.birthdate = userDTO.getBirthdate();
    this.isActive = true;
    this.isAdministrator = false;
  }

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

  public UserDTO toDTO() {
    return UserDTO.builder()
        .id(id)
        .firstName(firstName)
        .lastName(lastName)
        .email(email)
        .birthdate(birthdate)
        .isActive(isActive)
        .isAdministrator(isAdministrator)
        .build();
  }

  public Collection<? extends GrantedAuthority> getAuthorities() {
    Set<GrantedAuthority> authorities = new HashSet<>();

    authorities.add(() -> "ROLE_USER");

    if (isAdministrator) {
      authorities.add(() -> "ROLE_ADMIN");
    }

    return authorities;
  }

  @Override
  public String getUsername() {
    return email;
  }
}
