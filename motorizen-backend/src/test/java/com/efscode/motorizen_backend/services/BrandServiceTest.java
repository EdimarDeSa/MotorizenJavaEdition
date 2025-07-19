package com.efscode.motorizen_backend.services;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.efscode.motorizen_backend.models.brand.BrandEntity;
import com.efscode.motorizen_backend.models.brand.BrandUpdatesDTO;
import com.efscode.motorizen_backend.models.brand.NewBrandDTO;
import com.efscode.motorizen_backend.repositories.BrandRepository;

import jakarta.transaction.Transactional;

@SpringBootTest(properties = "spring.profiles.active=test")
@DisplayName("BrandService")
public class BrandServiceTest {
  @Autowired
  private BrandService brandService;

  @Autowired
  private BrandRepository brandRepo;

  private String marca1 = "Marca Teste 1";
  private String marca2 = "Marca Teste 2";
  private String marca3 = "Marca Teste 3";

  @Test
  @DisplayName("Deve criar uma nova marca com sucesso")
  @Transactional
  void testCreateBrandSuccess() {
    // Given
    NewBrandDTO newBrand = new NewBrandDTO(marca1);

    // When
    brandService.createBrand(newBrand);

    // Then
    assert true;
  }

  @Test
  @DisplayName("Deve deletar uma marca com sucesso")
  @Transactional
  void testDeleteBrandSuccess() {
    //
    BrandEntity brand = createBrand(marca1);

    // When
    brandService.deleteBrand(brand.getId());

    // Then
    BrandEntity deletedBrand = brandRepo.findById(brand.getId()).get();

    assertNotNull(deletedBrand.getDeletedAt());
  }

  @Test
  @DisplayName("Deve filtrar as marcas com sucesso")
  @Transactional
  void testFilterBrandsSuccess() {
    // Given
    String filter = "Marca";

    createBrand(marca1);
    createBrand(marca2);

    // When
    var filteredBrands = brandService.filterBrands(filter);

    // Then
    assertNotNull(filteredBrands);
    assertTrue(filteredBrands.size() == 2);
  }

  @Test
  @DisplayName("Deve retornar todas as marcas com sucesso")
  @Transactional
  void testFindAllBrandsSuccess() {
    // Given
    createBrand(marca1);
    createBrand(marca2);
    createBrand(marca3);

    // When
    var brands = brandService.findAllBrands();

    // Then
    assertNotNull(brands);
    assertTrue(brands.size() >= 3);
  }

  @Test
  @DisplayName("Deve atualizar uma marca com sucesso")
  @Transactional
  void testUpdateBrandSuccess() {
    // Given
    BrandEntity brand = createBrand(marca1);
    String newName = "Nova Marca";
    var brandUpdates = new BrandUpdatesDTO(newName);

    // When
    var updatedBrand = brandService.updateBrand(brand.getId(), brandUpdates);

    // Then
    assertNotNull(updatedBrand);
    assertTrue(updatedBrand.name().equals(newName));

  }

  private BrandEntity createBrand(String name) {
    BrandEntity brand = new BrandEntity(
        null,
        name,
        LocalDateTime.now(),
        LocalDateTime.now(),
        null);

    brandRepo.save(brand);

    return brandRepo.findByName(name);
  }
}
