package com.efscode.motorizen_backend.utils.validators;

import java.time.LocalDate;
import java.time.Period;

import com.efscode.motorizen_backend.enums.MotoriZenResponseCodeEnum;
import com.efscode.motorizen_backend.errors.MotorizenException;

public abstract class BaseValidators {
  protected void throwException(MotoriZenResponseCodeEnum rc) {
    throw new MotorizenException(rc);
  }

  protected Boolean isValidLength(String toValidate, int minLength, int maxLength) {
    return toValidate.length() >= minLength && toValidate.length() <= maxLength;
  }

  protected Boolean isValidEmail(String email) {
    return email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");
  }

  protected Boolean hasLowerCase(String password, Integer qtd) {
    return password.matches(".*([a-z]){" + qtd + ",}.*");
  }

  protected Boolean hasUpperCase(String password, Integer qtd) {
    return password.matches(".*([A-Z]){" + qtd + ",}.*");
  }

  protected Boolean hasDigit(String password, Integer qtd) {
    return password.matches(".*(\\d){" + qtd + ",}.*");
  }

  protected Boolean hasSpecialChar(String password, Integer qtd) {
    return password.matches(".*([@$!%*?&].*){" + qtd + ",}");
  }

  protected Boolean hasAge(LocalDate birthdate) {
    return Period.between(birthdate, LocalDate.now()).getYears() >= 18;
  }
}
