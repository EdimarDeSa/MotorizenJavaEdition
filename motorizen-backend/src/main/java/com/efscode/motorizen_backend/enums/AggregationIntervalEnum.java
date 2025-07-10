package com.efscode.motorizen_backend.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum AggregationIntervalEnum {
  DAILY("day"),
  WEEKLY("week"),
  MONTHLY("month"),
  YEARLY("year");

  private final String value;
}
