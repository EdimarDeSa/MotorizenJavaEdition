package com.efscode.motorizen_backend.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.efscode.motorizen_backend.enums.MotoriZenResponseCodeEnum;
import com.efscode.motorizen_backend.models.dtos.ApiResponseBody;
import com.efscode.motorizen_backend.models.dtos.FuelTypeDTO;
import com.efscode.motorizen_backend.models.dtos.NewFuelType;
import com.efscode.motorizen_backend.services.FuelTypeService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/fuel-type")
@RequiredArgsConstructor
@Slf4j
public class FuelTypeController {
  private final FuelTypeService fuelTypeService;

  @GetMapping("/all")
  public ResponseEntity<ApiResponseBody<List<FuelTypeDTO>>> getAllFuelTypes() {
    List<FuelTypeDTO> fuelTypes = fuelTypeService.findAllFuelTypes();

    ApiResponseBody<List<FuelTypeDTO>> content = ApiResponseBody.ok(fuelTypes);

    return ResponseEntity.ok(content);
  }

  @PostMapping("/new")
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  public ResponseEntity<ApiResponseBody<Void>> createNewFuelType(@RequestBody NewFuelType newFuelType) {
    log.debug("Iniciando createNewFuelType para o tipo de combustível`{}`", newFuelType.name());
    fuelTypeService.createNewFuelType(newFuelType);

    ApiResponseBody<Void> content = new ApiResponseBody<>(
        MotoriZenResponseCodeEnum.OK, null);

    return ResponseEntity.status(HttpStatus.CREATED).body(content);
  }

}
