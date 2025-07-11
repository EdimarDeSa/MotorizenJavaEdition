package com.efscode.motorizen_backend.models.entitys;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.efscode.motorizen_backend.interfaces.EntityInterface;
import com.efscode.motorizen_backend.models.dtos.FuelTypeDTO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "fuel_type")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FuelTypeEntity implements EntityInterface<FuelTypeDTO> {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  private Integer id;

  @Column(nullable = false, unique = true, length = 20)
  private String name;

  @CreationTimestamp
  @Column(nullable = false, updatable = false)
  private LocalDateTime createdAt;

  @UpdateTimestamp
  @Column(nullable = false)
  private LocalDateTime updatedAt;

  @Column(nullable = true)
  private LocalDateTime deletedAt;

  public FuelTypeEntity(FuelTypeDTO fuelTypeDTO) {
    this.id = fuelTypeDTO.id();
    this.name = fuelTypeDTO.name();
  }

  @Override
  public FuelTypeDTO toDTO() {
    return FuelTypeDTO.builder()
        .id(id)
        .name(name)
        .build();
  }
}
