package com.efscode.motorizen_backend.models.vehicle;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.efscode.motorizen_backend.models.brand.BrandEntity;
import com.efscode.motorizen_backend.models.fuel_type.FuelTypeEntity;
import com.efscode.motorizen_backend.models.user.UserEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "vehicle")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class VehicleEntity {
  @Id
  private UUID id;

  @ManyToOne
  @JoinColumn(nullable = false, name = "user_id")
  private UserEntity user;

  @ManyToOne()
  @JoinColumn(nullable = false, name = "brand_id")
  private BrandEntity brand;

  @ManyToOne()
  @JoinColumn(nullable = false, name = "fuel_type_id")
  private FuelTypeEntity fuelType;

  @Column(nullable = false, length = 100)
  private String model;

  @Column(nullable = true, length = 11, unique = true)
  private String renavam;

  @Column(nullable = false)
  private Integer year;

  @Column(nullable = false, length = 25)
  private String color;

  @Column(nullable = true, length = 10, unique = true)
  private String licensePlate;

  @Column(nullable = false, precision = 10, scale = 4)
  private BigDecimal fuelCapacity;

  @Column(nullable = false, precision = 10, scale = 4)
  private BigDecimal odometer;

  @Column
  private Boolean isActive;

  @CreationTimestamp
  @Column(updatable = false)
  private LocalDateTime createdAt;

  @UpdateTimestamp
  @Column(nullable = false)
  private LocalDateTime updatedAt;

  @Column(nullable = true)
  private LocalDateTime deletedAt;
}
