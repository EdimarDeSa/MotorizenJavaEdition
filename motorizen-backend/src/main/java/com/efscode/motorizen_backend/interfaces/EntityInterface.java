package com.efscode.motorizen_backend.interfaces;

public interface EntityInterface<T extends Record> {
  T toDTO();
}
