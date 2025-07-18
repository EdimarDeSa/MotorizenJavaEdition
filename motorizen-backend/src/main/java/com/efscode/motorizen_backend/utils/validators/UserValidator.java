package com.efscode.motorizen_backend.utils.validators;

import java.util.UUID;

import org.springframework.stereotype.Component;

import com.efscode.motorizen_backend.enums.MotoriZenResponseCodeEnum;
import com.efscode.motorizen_backend.models.user.NewUserDTO;
import com.efscode.motorizen_backend.models.user.UserUpdateEmailDTO;
import com.efscode.motorizen_backend.models.user.UserUpdatesDTO;
import com.efscode.motorizen_backend.repositories.UserRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UserValidator extends BaseValidators {
  private final UserRepository userRepo;

  public void validateNewUser(NewUserDTO newUser) {
    if (!isValidLength(newUser.firstName(), 2, 50))
      throwException(MotoriZenResponseCodeEnum.INVALID_USER_NAME);

    if (!isValidLength(newUser.lastName(), 2, 100))
      throwException(MotoriZenResponseCodeEnum.INVALID_USER_NAME);

    if (!hasAge(newUser.birthdate()))
      throwException(MotoriZenResponseCodeEnum.USER_MUST_BE_18);

    if (userRepo.existsByEmail(newUser.email().toLowerCase()))
      throwException(MotoriZenResponseCodeEnum.USER_ALREADY_EXISTS);

    if (!isValidEmail(newUser.email()))
      throwException(MotoriZenResponseCodeEnum.INVALID_USER_EMAIL);

    if (!isValidLength(newUser.password(), 8, 100))
      throwException(MotoriZenResponseCodeEnum.INVALID_PASSWORD);

    if (!hasLowerCase(newUser.password(), 1))
      throwException(MotoriZenResponseCodeEnum.INVALID_PASSWORD);

    if (!hasUpperCase(newUser.password(), 1))
      throwException(MotoriZenResponseCodeEnum.INVALID_PASSWORD);

    if (!hasDigit(newUser.password(), 1))
      throwException(MotoriZenResponseCodeEnum.INVALID_PASSWORD);

    if (!hasSpecialChar(newUser.password(), 1))
      throwException(MotoriZenResponseCodeEnum.INVALID_PASSWORD);
  }

  public void validateUserUpdates(UserUpdatesDTO userUpdates) {
    if (!isValidLength(userUpdates.firstName(), 2, 50))
      throwException(MotoriZenResponseCodeEnum.INVALID_USER_NAME);

    if (!isValidLength(userUpdates.lastName(), 2, 100))
      throwException(MotoriZenResponseCodeEnum.INVALID_USER_NAME);

    if (!hasAge(userUpdates.birthdate()))
      throwException(MotoriZenResponseCodeEnum.USER_MUST_BE_18);
  }

  public void validateNewEmail(UserUpdateEmailDTO emailUpdate, UUID id) {
    if (userRepo.existsByEmailAndIdNot(emailUpdate.email().toLowerCase(), id))
      throwException(MotoriZenResponseCodeEnum.USER_ALREADY_EXISTS);

    if (!isValidEmail(emailUpdate.email()))
      throwException(MotoriZenResponseCodeEnum.INVALID_USER_EMAIL);
  }

  public void validateNewPassword(String password) {
    if (!isValidLength(password, 8, 100))
      throwException(MotoriZenResponseCodeEnum.INVALID_PASSWORD);

    if (!hasLowerCase(password, 1))
      throwException(MotoriZenResponseCodeEnum.INVALID_PASSWORD);

    if (!hasUpperCase(password, 1))
      throwException(MotoriZenResponseCodeEnum.INVALID_PASSWORD);

    if (!hasDigit(password, 1))
      throwException(MotoriZenResponseCodeEnum.INVALID_PASSWORD);

    if (!hasSpecialChar(password, 1))
      throwException(MotoriZenResponseCodeEnum.INVALID_PASSWORD);
  }

}
