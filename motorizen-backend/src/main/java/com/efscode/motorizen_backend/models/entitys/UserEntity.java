package com.efscode.motorizen_backend.models.entitys;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.efscode.motorizen_backend.interfaces.EntityInterface;
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
public class UserEntity implements EntityInterface<UserDTO> {
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
  @Column(nullable = false, updatable = false)
  private LocalDateTime createdAt;

  @UpdateTimestamp
  @Column(nullable = false)
  private LocalDateTime updatedAt;

  @Column(nullable = true)
  private LocalDateTime deletedAt;

  @ToString.Exclude
  @EqualsAndHashCode.Exclude
  @OneToMany(mappedBy = "user")
  private Set<VehicleEntity> vehicles;

  public UserEntity(UserDTO userDTO) {
    this.id = userDTO.id();
    this.firstName = userDTO.firstName();
    this.lastName = userDTO.lastName();
    this.email = userDTO.email();
    this.birthdate = userDTO.birthdate();
    this.isActive = userDTO.isActive();
    this.isAdministrator = userDTO.isAdministrator();
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
        .build();
  }
}
