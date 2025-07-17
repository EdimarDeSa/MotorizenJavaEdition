package com.efscode.motorizen_backend.Utils;

import org.springframework.stereotype.Component;

import com.efscode.motorizen_backend.enums.MotoriZenResponseCodeEnum;
import com.efscode.motorizen_backend.errors.MotorizenException;
import com.efscode.motorizen_backend.models.brand.BrandDTO;
import com.efscode.motorizen_backend.models.fuel_type.FuelTypeDTO;
import com.efscode.motorizen_backend.models.fuel_type.NewFuelTypeDTO;
import com.efscode.motorizen_backend.models.user.NewUser;
import com.efscode.motorizen_backend.models.user.UserDTO;
import com.efscode.motorizen_backend.repositorys.BackLogRepository;
import com.efscode.motorizen_backend.repositorys.BrandRepository;
import com.efscode.motorizen_backend.repositorys.FuelTypeRepository;
import com.efscode.motorizen_backend.repositorys.RegisterRepository;
import com.efscode.motorizen_backend.repositorys.UserRepository;
import com.efscode.motorizen_backend.repositorys.VehicleRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Component
public class Validators {
  private final UserRepository userRepo;
  private final BrandRepository brandRepo;
  private final VehicleRepository vehicleRepo;
  private final FuelTypeRepository fuelTypeRepo;
  private final RegisterRepository registerRepo;
  private final BackLogRepository backLogRepo;

  public void validateNewBrand(BrandDTO brand) {
    validateBrandData(brand);

    if (brandRepo.existsByNameAndDeletedAtIsNull(brand.name()))
      throw new MotorizenException(MotoriZenResponseCodeEnum.BRAND_ALREADY_EXISTS);
  }

  public void validateBrandUpdates(BrandDTO brand, Integer id) {
    validateBrandData(brand);

    if (brandRepo.existsByNameAndIdNot(brand.name(), id))
      throw new MotorizenException(MotoriZenResponseCodeEnum.BRAND_ALREADY_EXISTS);
  }

  private void validateBrandData(BrandDTO brand) {
    validateLength(brand.name(), 2, 50, MotoriZenResponseCodeEnum.INVALID_BRAND_NAME);
  }

  public void validateUser(UserDTO user) {
    validateLength(user.firstName(), 2, 50, MotoriZenResponseCodeEnum.INVALID_USER_NAME);
    validateLength(user.lastName(), 2, 100, MotoriZenResponseCodeEnum.INVALID_USER_NAME);
    validateLength(user.email(), 2, 255, MotoriZenResponseCodeEnum.INVALID_USER_EMAIL);

    if (user.getAge() < 18) {
      throw new MotorizenException(MotoriZenResponseCodeEnum.USER_MUST_BE_18);
    }

    if (!user.email().matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")) {
      throw new MotorizenException(MotoriZenResponseCodeEnum.INVALID_USER_EMAIL);
    }
  }

  public void validateNewUser(NewUser newUser) {
    this.validateUser(newUser.toUserDTO());
    if (userRepo.existsByEmail(newUser.getEmail().toLowerCase())) {
      throw new MotorizenException(MotoriZenResponseCodeEnum.USER_ALREADY_EXISTS);
    }

    if (!newUser.getPassword().matches(
        "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$")) {
      throw new MotorizenException(MotoriZenResponseCodeEnum.INVALID_PASSWORD);
    }
  }

  private void validateLength(String toValidate, int minLength, int maxLength, MotoriZenResponseCodeEnum rc) {
    if (toValidate.length() < minLength || toValidate.length() > maxLength) {
      throw new MotorizenException(rc);
    }
  }

  public void validateFuelType(FuelTypeDTO dto) {
    validateLength(dto.name(), 2, 20, MotoriZenResponseCodeEnum.INVALID_FUEL_TYPE_NAME);
  }

  public void validateNewFuelType(NewFuelTypeDTO dto) {
    validateFuelType(dto.toDTO());

    if (fuelTypeRepo.existsByName(dto.name())) {
      throw new MotorizenException(MotoriZenResponseCodeEnum.FUEL_TYPE_ALREADY_EXISTS);
    }
  }
}
