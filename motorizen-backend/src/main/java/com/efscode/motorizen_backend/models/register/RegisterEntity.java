package com.efscode.motorizen_backend.models.register;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.efscode.motorizen_backend.models.user.UserEntity;
import com.efscode.motorizen_backend.models.vehicle.VehicleEntity;

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

@Entity
@Table(name = "register")
@Data
@AllArgsConstructor
@Builder
public class RegisterEntity {
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
  @Column(updatable = false)
  private LocalDateTime createdAt;

  @UpdateTimestamp
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
}
