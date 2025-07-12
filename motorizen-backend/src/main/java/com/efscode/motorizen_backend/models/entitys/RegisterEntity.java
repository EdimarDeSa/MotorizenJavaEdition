package com.efscode.motorizen_backend.models.entitys;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.efscode.motorizen_backend.interfaces.EntityInterface;
import com.efscode.motorizen_backend.models.dtos.RegisterDTO;

@Entity
@Table(name = "register")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisterEntity implements EntityInterface<RegisterDTO> {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @OneToOne
  @JoinColumn(nullable = false, updatable = false, name = "user_id")
  private UserEntity user;

  @OneToOne
  @JoinColumn(nullable = false, updatable = false, name = "vehicle_id")
  private VehicleEntity vehicle;

  @Column(nullable = false)
  private Integer numberOfTrips;

  @Column(nullable = false, precision = 10, scale = 4)
  private BigDecimal distance;

  @Column(nullable = false, precision = 10, scale = 4)
  private BigDecimal meanConsuption;

  @Column(nullable = false, precision = 10, scale = 2)
  private BigDecimal value;

  @Column(nullable = false)
  private LocalTime workTime;

  @Column(nullable = false)
  private LocalDate registerDate;

  @CreationTimestamp
  @Column(nullable = false, updatable = false)
  private LocalDateTime createdAt;

  @UpdateTimestamp
  @Column(nullable = false)
  private LocalDateTime updatedAt;

  @Column(nullable = true)
  private LocalDateTime deletedAt;

  public RegisterEntity(RegisterDTO registryDTO, UserEntity user, VehicleEntity vehicle) {
    this.id = registryDTO.id();
    this.user = user;
    this.vehicle = vehicle;
    this.numberOfTrips = registryDTO.numberOfTrips();
    this.distance = registryDTO.distance();
    this.meanConsuption = registryDTO.meanConsuption();
    this.value = registryDTO.value();
    this.workTime = registryDTO.workTime();
    this.registerDate = registryDTO.registerDate();
  }

  @Override
  public RegisterDTO toDTO() {
    return RegisterDTO.builder()
        .id(id)
        .user(user.toDTO())
        .vehicle(vehicle.toDTO())
        .numberOfTrips(numberOfTrips)
        .distance(distance)
        .meanConsuption(meanConsuption)
        .value(value)
        .workTime(workTime)
        .registerDate(registerDate)
        .build();
  }
}
