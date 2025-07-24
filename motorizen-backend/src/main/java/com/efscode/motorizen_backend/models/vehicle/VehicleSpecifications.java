package com.efscode.motorizen_backend.models.vehicle;

import java.util.UUID;

import org.springframework.data.jpa.domain.Specification;

public class VehicleSpecifications {

  public static Specification<VehicleEntity> hasUserId(UUID userId) {
    return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("user").get("id"), userId);
  }

  public static Specification<VehicleEntity> hasId(UUID vehicleId) {
    return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("id"), vehicleId);
  }

  public static Specification<VehicleEntity> hasBrandId(Integer brandId) {
    return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("brand").get("id"), brandId);
  }

  public static Specification<VehicleEntity> hasFuelTypeId(Integer fuelTypeId) {
    return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("fuelType").get("id"), fuelTypeId);
  }

  public static Specification<VehicleEntity> hasModel(String model) {
    return (root, query, criteriaBuilder) -> criteriaBuilder.like(criteriaBuilder.lower(root.get("model")),
        "%" + model.toLowerCase() + "%");
  }

  public static Specification<VehicleEntity> hasRenavam(String renavam) {
    return (root, query, criteriaBuilder) -> criteriaBuilder.like(criteriaBuilder.lower(root.get("renavam")),
        "%" + renavam.toLowerCase() + "%");
  }

  public static Specification<VehicleEntity> hasYear(Integer year) {
    return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("year"), year);
  }

  public static Specification<VehicleEntity> hasColor(String color) {
    return (root, query, criteriaBuilder) -> criteriaBuilder.like(criteriaBuilder.lower(root.get("color")),
        "%" + color.toLowerCase() + "%");
  }

  public static Specification<VehicleEntity> hasLicensePlate(String licensePlate) {
    return (root, query, criteriaBuilder) -> criteriaBuilder.like(criteriaBuilder.lower(root.get("licensePlate")),
        "%" + licensePlate.toLowerCase() + "%");
  }

  public static Specification<VehicleEntity> isActive(Boolean isActive) {
    return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("isActive"), isActive);
  }

  public static Specification<VehicleEntity> isDeleted(Boolean isDeleted) {
    return (root, query, criteriaBuilder) -> {
      if (isDeleted) {
        return criteriaBuilder.isNotNull(root.get("deletedAt"));
      } else {
        return criteriaBuilder.isNull(root.get("deletedAt"));
      }
    };
  }

  public static Specification<VehicleEntity> mountFilterSpecification(VehicleFilterDTO filter, UUID userId) {
    Specification<VehicleEntity> spec = hasUserId(userId);

    if (filter.id() != null) {
      spec = spec.and(hasId(filter.id()));
    }

    if (filter.brandId() != null) {
      spec = spec.and(hasBrandId(filter.brandId()));
    }

    if (filter.fuelTypeId() != null) {
      spec = spec.and(hasFuelTypeId(filter.fuelTypeId()));
    }

    if (filter.model() != null) {
      spec = spec.and(hasModel(filter.model()));
    }

    if (filter.renavam() != null) {
      spec = spec.and(hasRenavam(filter.renavam()));
    }

    if (filter.year() != null) {
      spec = spec.and(hasYear(filter.year()));
    }

    if (filter.color() != null) {
      spec = spec.and(hasColor(filter.color()));
    }

    if (filter.licensePlate() != null) {
      spec = spec.and(hasLicensePlate(filter.licensePlate()));
    }

    if (filter.isActive() != null) {
      spec = spec.and(isActive(filter.isActive()));
    }

    if (filter.isDeleted() != null) {
      spec = spec.and(isDeleted(filter.isDeleted()));
    }

    return spec;
  }
}
