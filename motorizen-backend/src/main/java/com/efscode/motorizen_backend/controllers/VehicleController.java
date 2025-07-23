package com.efscode.motorizen_backend.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.efscode.motorizen_backend.models.ApiResponseBody;
import com.efscode.motorizen_backend.models.user.UserDTO;
import com.efscode.motorizen_backend.models.vehicle.NewVehicleDTO;
import com.efscode.motorizen_backend.models.vehicle.VehicleDTO;
import com.efscode.motorizen_backend.models.vehicle.VehicleFilterDTO;
import com.efscode.motorizen_backend.models.vehicle.VehicleUpdatesDTO;
import com.efscode.motorizen_backend.services.VehicleService;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/vehicle")
@RequiredArgsConstructor
@Tag(name = "Vehicles", description = "Vehicles API")
@Slf4j
public class VehicleController {
  private final VehicleService vehicleService;

  @GetMapping("/all")
  public ResponseEntity<ApiResponseBody<List<VehicleDTO>>> findAllVehicles(
      @AuthenticationPrincipal UserDTO user) {
    log.debug("Iniciando findAllVehicles para o usuário`{}`", user.id());
    List<VehicleDTO> vehicles = vehicleService.findAllVehicles(user.id());

    ApiResponseBody<List<VehicleDTO>> content = ApiResponseBody.ok(vehicles);

    return ResponseEntity.ok(content);
  }

  @GetMapping("/details/{id}")
  public ResponseEntity<ApiResponseBody<VehicleDTO>> findVehicleById(@RequestParam UUID vehicleId,
      @AuthenticationPrincipal UserDTO user) {
    log.debug("Iniciando findVehicleById para o id`{}` do usuário`{}`", vehicleId, user.id());
    VehicleDTO vehicle = vehicleService.findVehicleById(vehicleId, user.id());

    ApiResponseBody<VehicleDTO> content = ApiResponseBody.ok(vehicle);

    return ResponseEntity.ok(content);
  }

  @GetMapping("/filter")
  public ResponseEntity<ApiResponseBody<List<VehicleDTO>>> filterVehicles(
      @RequestBody VehicleFilterDTO filter,
      @AuthenticationPrincipal UserDTO user) {
    log.debug("Iniciando filterVehicles para o filtro`{}` do usuário`{}`", filter, user.id());
    List<VehicleDTO> vehicles = vehicleService.filterVehicles(filter, user.id());

    ApiResponseBody<List<VehicleDTO>> content = ApiResponseBody.ok(vehicles);

    return ResponseEntity.ok(content);
  }

  @PostMapping("/new")
  public ResponseEntity<ApiResponseBody<VehicleDTO>> createVehicle(
      @RequestBody NewVehicleDTO newVehicle,
      @AuthenticationPrincipal UserDTO user) {
    log.debug("Iniciando createVehicle para o veículo`{}`", newVehicle.model());
    VehicleDTO createdVehicle = vehicleService.createVehicle(newVehicle, user.id());

    ApiResponseBody<VehicleDTO> content = ApiResponseBody.ok(createdVehicle);

    return ResponseEntity.status(HttpStatus.CREATED).body(content);
  }

  @PutMapping("/update/{id}")
  public ResponseEntity<ApiResponseBody<VehicleDTO>> updateVehicle(@PathVariable UUID vehicleId,
      @RequestBody VehicleUpdatesDTO vehicleUpdates, @AuthenticationPrincipal UserDTO user) {
    log.debug("Iniciando updateVehicle para o id`{}` do usuário`{}`", vehicleId, user.id());
    VehicleDTO updatedVehicle = vehicleService.updateVehicle(vehicleId, vehicleUpdates, user.id());

    ApiResponseBody<VehicleDTO> content = ApiResponseBody.ok(updatedVehicle);

    return ResponseEntity.ok(content);

  }

  @DeleteMapping("/delete/{id}")
  public ResponseEntity<ApiResponseBody<Void>> deleteVehicle(@PathVariable UUID vehicleId,
      @AuthenticationPrincipal UserDTO user) {
    log.debug("Iniciando deleteVehicle para o id`{}` do usuário`{}`", vehicleId, user.id());

    vehicleService.deleteVehicle(vehicleId, user.id());
    ApiResponseBody<Void> content = ApiResponseBody.ok(null);

    return ResponseEntity.ok(content);
  }
}
