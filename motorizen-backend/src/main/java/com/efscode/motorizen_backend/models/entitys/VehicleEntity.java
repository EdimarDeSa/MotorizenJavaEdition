package com.efscode.motorizen_backend.models.entitys;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.efscode.motorizen_backend.interfaces.EntityInterface;
import com.efscode.motorizen_backend.models.dtos.VehicleDTO;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "vehicle")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VehicleEntity implements EntityInterface<VehicleDTO> {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @ManyToOne(cascade = CascadeType.ALL)
  @JoinColumn(nullable = false, name = "user_id")
  private UserEntity user;

  @ManyToOne(cascade = CascadeType.PERSIST)
  @JoinColumn(nullable = false, name = "brand_id")
  private BrandEntity brand;

  @ManyToOne(cascade = CascadeType.PERSIST)
  @JoinColumn(nullable = true, name = "fuel_type_id")
  private FuelTypeEntity fuelType;

  @Column(nullable = false, length = 100)
  private String model;

  @Column(nullable = false, length = 11)
  private String renavam;

  @Column(nullable = false)
  private Integer year;

  @Column(nullable = false, length = 25)
  private String color;

  @Column(nullable = false, length = 10)
  private String licensePlate;

  @Column(nullable = false, precision = 10, scale = 4)
  private Double fuelCapacity;

  @Column(nullable = false, precision = 10, scale = 4)
  private Double odometer;

  @Column(nullable = false)
  @Builder.Default
  private Boolean isActive = true;

  @CreationTimestamp
  @Column(nullable = false, updatable = false)
  private LocalDateTime createdAt;

  @UpdateTimestamp
  @Column(nullable = false)
  private LocalDateTime updatedAt;

  @Column(nullable = true)
  private LocalDateTime deletedAt;

  public VehicleEntity(VehicleDTO vehicleDTO, UserEntity user, BrandEntity brand, FuelTypeEntity vehicle) {
    this.id = vehicleDTO.id();
    this.user = user;
    this.brand = brand;
    this.fuelType = vehicle;
    this.model = vehicleDTO.model();
    this.year = vehicleDTO.year();
    this.renavam = vehicleDTO.renavam();
    this.color = vehicleDTO.color();
    this.licensePlate = vehicleDTO.licensePlate();
    this.fuelCapacity = vehicleDTO.fuelCapacity();
    this.odometer = vehicleDTO.odometer();
    this.isActive = vehicleDTO.isActive();
  }

  @Override
  public VehicleDTO toDTO() {
    return VehicleDTO.builder()
        .id(id)
        .user(user.toDTO())
        .brand(brand.toDTO())
        .fuelType(fuelType.toDTO())
        .model(model)
        .renavam(renavam)
        .color(color)
        .year(year)
        .licensePlate(licensePlate)
        .fuelCapacity(fuelCapacity)
        .odometer(odometer)
        .isActive(isActive)
        .build();
  }
}
