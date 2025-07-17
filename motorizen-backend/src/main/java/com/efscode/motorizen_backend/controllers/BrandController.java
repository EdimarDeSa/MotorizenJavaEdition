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

import com.efscode.motorizen_backend.models.brand.BrandDTO;
import com.efscode.motorizen_backend.models.brand.BrandUpdatesDTO;
import com.efscode.motorizen_backend.models.brand.NewBrandDTO;
import com.efscode.motorizen_backend.models.dtos.ApiResponseBody;
import com.efscode.motorizen_backend.services.BrandService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/brand")
@RequiredArgsConstructor
@Slf4j
public class BrandController {
  private final BrandService brandService;

  @GetMapping("/all")
  public ResponseEntity<ApiResponseBody<List<BrandDTO>>> getAllBrands() {
    log.debug("Iniciando getAllBrands");
    List<BrandDTO> brands = brandService.findAllBrands();

    ApiResponseBody<List<BrandDTO>> content = ApiResponseBody.ok(brands);

    return ResponseEntity.ok(content);
  }

  @GetMapping("/search/{filter}")
  public ResponseEntity<ApiResponseBody<List<BrandDTO>>> filterBrands(@PathVariable String filter) {
    log.debug("Iniciando filterBrands para o filtro`{}`", filter);
    List<BrandDTO> brands = brandService.filterBrands(filter);

    ApiResponseBody<List<BrandDTO>> content = ApiResponseBody.ok(brands);

    return ResponseEntity.ok(content);
  }

  @PostMapping("/new")
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  public ResponseEntity<ApiResponseBody<Void>> createNewBrand(@RequestBody NewBrandDTO newBrand) {
    log.debug("Iniciando createNewBrand para a marca`{}`", newBrand.name());
    brandService.createNewBrand(newBrand);

    ApiResponseBody<Void> content = ApiResponseBody.ok(null);

    return ResponseEntity.status(HttpStatus.CREATED).body(content);
  }

  @PutMapping("update/{id}")
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  public ResponseEntity<ApiResponseBody<BrandDTO>> updateBrand(@PathVariable Integer id,
      @RequestBody BrandUpdatesDTO brandUpdates) {
    log.debug("Iniciando updateBrand para a marca`{}`", brandUpdates.name());

    BrandDTO brand = brandService.updateBrand(id, brandUpdates);
    ApiResponseBody<BrandDTO> content = ApiResponseBody.ok(brand);

    return ResponseEntity.ok(content);
  }

  @DeleteMapping("/delete/{id}")
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  public ResponseEntity<ApiResponseBody<Void>> deleteBrand(@PathVariable Integer id) {
    log.debug("Iniciando deleteBrand para o id`{}`", id);

    brandService.deleteBrand(id);
    ApiResponseBody<Void> content = ApiResponseBody.ok(null);

    return ResponseEntity.ok(content);
  }
}
