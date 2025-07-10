package com.efscode.motorizen_backend.models.entitys;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

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
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "vehicle")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class VehicleEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @ManyToOne(cascade = CascadeType.ALL)
  @JoinColumn(nullable = false)
  private UserEntity userId;

  @ManyToOne(cascade = CascadeType.PERSIST)
  @JoinColumn(nullable = false)
  private BrandEntity brandId;

  @ManyToOne(cascade = CascadeType.PERSIST)
  @JoinColumn(nullable = true)
  private FuelTypeEntity fuelTypeId;

  @Column(nullable = false, length = 100)
  private String model;
  
  @Column(nullable = false, length = 11)
  private String renavam;
  
  @Column(nullable = false, length = 25)
  private String color;
  
  @Column(nullable = false, length = 10)
  private String licensePlate;
  
  @Column(nullable = false, precision = 10, scale = 2)
  private Double fuelCapacity;
  
  @Column(nullable = false, precision = 10, scale = 2)
  private Double odometer;
  
  @Column(nullable = false)
  private Boolean isActive;

  @CreationTimestamp
  @Column(nullable = false, updatable = false)
  private LocalDateTime createdAt;

  @UpdateTimestamp
  @Column(nullable = false)
  private LocalDateTime updatedAt;

  @Column(nullable = true)
  private LocalDateTime deletedAt;
}
