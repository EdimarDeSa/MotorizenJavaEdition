package com.efscode.motorizen_backend.models.entitys;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.efscode.motorizen_backend.interfaces.EntityInterface;
import com.efscode.motorizen_backend.models.dtos.RegisterDTO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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

  @OneToMany
  @JoinColumn(nullable = false, updatable = false)
  private UserEntity user;

  @OneToMany
  @JoinColumn(nullable = false)
  private VehicleEntity vehicle;

  @Column(nullable = false)
  private Integer trips;

  @Column(nullable = false, precision = 10, scale = 2)
  private Double distance;

  @Column(nullable = false, precision = 10, scale = 2)
  private Double meanConsuption;

  @Column(nullable = false, precision = 10, scale = 2)
  private BigDecimal value;

  @Column(nullable = false)
  private LocalTime workTime;

  @Column(nullable = false)
  private LocalDate date;

  @CreationTimestamp
  @Column(nullable = false, updatable = false)
  private LocalDateTime createdAt;

  @UpdateTimestamp
  @Column(nullable = false)
  private LocalDateTime updatedAt;

  @Column(nullable = true)
  private LocalDateTime deletedAt;

  public RegisterEntity(RegisterDTO brandDTO, UserEntity user, VehicleEntity vehicle) {
    this.id = brandDTO.id();
    this.user = user;
    this.vehicle = vehicle;
    this.trips = brandDTO.trips();
    this.distance = brandDTO.distance();
    this.meanConsuption = brandDTO.meanConsuption();
    this.value = brandDTO.value();
    this.workTime = brandDTO.workTime();
    this.date = brandDTO.date();
  }

  @Override
  public RegisterDTO toDTO() {
    return RegisterDTO.builder()
        .id(id)
        .user(user.toDTO())
        .vehicle(vehicle.toDTO())
        .licensePlate(vehicle.getLicensePlate())
        .trips(trips)
        .distance(distance)
        .meanConsuption(meanConsuption)
        .value(value)
        .workTime(workTime)
        .date(date)
        .build();
  }
}
