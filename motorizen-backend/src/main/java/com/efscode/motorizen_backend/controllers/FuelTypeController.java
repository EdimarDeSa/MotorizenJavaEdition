package com.efscode.motorizen_backend.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.efscode.motorizen_backend.enums.MotoriZenResponseCodeEnum;
import com.efscode.motorizen_backend.models.ApiResponseBody;
import com.efscode.motorizen_backend.models.fuel_type.FuelTypeDTO;
import com.efscode.motorizen_backend.models.fuel_type.FuelTypeUpdatesDTO;
import com.efscode.motorizen_backend.models.fuel_type.NewFuelTypeDTO;
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

  @GetMapping("/search/{filter}")
  public ResponseEntity<ApiResponseBody<List<FuelTypeDTO>>> filterFuelTypes(@PathVariable String filter) {
    log.debug("Iniciando filterFuelTypes para o filtro`{}`", filter);
    List<FuelTypeDTO> fuelTypes = fuelTypeService.filterFuelTypes(filter);

    ApiResponseBody<List<FuelTypeDTO>> content = ApiResponseBody.ok(fuelTypes);

    return ResponseEntity.ok(content);
  }

  @PostMapping("/new")
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  public ResponseEntity<ApiResponseBody<Void>> createNewFuelType(@RequestBody NewFuelTypeDTO newFuelType) {
    log.debug("Iniciando createNewFuelType para o tipo de combustível`{}`", newFuelType.name());
    fuelTypeService.createNewFuelType(newFuelType);

    ApiResponseBody<Void> content = new ApiResponseBody<>(
        MotoriZenResponseCodeEnum.OK, null);

    return ResponseEntity.status(HttpStatus.CREATED).body(content);
  }

  @PutMapping("update/{id}")
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  public ResponseEntity<ApiResponseBody<FuelTypeDTO>> updateFuelType(@PathVariable Integer id,
      @RequestBody FuelTypeUpdatesDTO fuelTypeUpdates) {
    log.debug("Iniciando updateFuelType para a marca`{}`", fuelTypeUpdates.name());

    FuelTypeDTO fuelType = fuelTypeService.updateFuelType(id, fuelTypeUpdates);
    ApiResponseBody<FuelTypeDTO> content = ApiResponseBody.ok(fuelType);

    return ResponseEntity.ok(content);
  }

  @DeleteMapping("/delete/{id}")
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  public ResponseEntity<ApiResponseBody<Void>> deleteFuelType(@PathVariable Integer id) {
    log.debug("Iniciando deleteFuelType para o id`{}`", id);

    fuelTypeService.deleteFuelType(id);
    ApiResponseBody<Void> content = ApiResponseBody.ok(null);

    return ResponseEntity.ok(content);
  }

}
