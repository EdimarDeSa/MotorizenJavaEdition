package com.efscode.motorizen_backend.models.entitys;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.efscode.motorizen_backend.interfaces.EntityInterface;
import com.efscode.motorizen_backend.models.dtos.BrandDTO;

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
@Table(name = "brand")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BrandEntity implements EntityInterface<BrandDTO> {
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

  public BrandEntity(BrandDTO brandDTO) {
    this.id = brandDTO.id();
    this.name = brandDTO.name();
  }

  @Override
  public BrandDTO toDTO() {
    return BrandDTO.builder()
        .id(id)
        .name(name)
        .build();
  }
}
