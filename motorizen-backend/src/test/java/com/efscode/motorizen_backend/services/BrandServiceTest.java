package com.efscode.motorizen_backend.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import com.efscode.motorizen_backend.enums.MotoriZenResponseCodeEnum;
import com.efscode.motorizen_backend.errors.MotorizenException;
import com.efscode.motorizen_backend.models.brand.BrandDTO;
import com.efscode.motorizen_backend.models.brand.BrandEntity;
import com.efscode.motorizen_backend.models.brand.BrandMapper;
import com.efscode.motorizen_backend.models.brand.BrandUpdatesDTO;
import com.efscode.motorizen_backend.models.brand.NewBrandDTO;
import com.efscode.motorizen_backend.repositories.BrandRepository;
import com.efscode.motorizen_backend.utils.validators.BrandValidator;

@DataJpaTest(showSql = false)
@Import(BrandService.class)
@DisplayName("BrandService")
@ActiveProfiles("test")
public class BrandServiceTest {

  @Autowired
  private BrandService brandService;

  @Autowired
  private BrandRepository brandRepo;

  @MockitoBean
  private BrandValidator validator;

  @MockitoBean
  private BrandMapper brandMapper;

  private String marca1 = "Marca Teste 1";
  private String marca2 = "Marca Teste 2";
  private String marca3 = "Marca Teste 3";

  @BeforeEach
  void setUp() {
    // Mocka validator
    doNothing().when(validator).validateNewBrand(ArgumentMatchers.any(NewBrandDTO.class));
    doNothing().when(validator).validateBrandUpdates(ArgumentMatchers.any(BrandUpdatesDTO.class),
        ArgumentMatchers.anyInt());

    // Mocka brandMapper
    when(brandMapper.entityToDto(any(BrandEntity.class)))
        .thenAnswer(invocation -> {
          BrandEntity entity = invocation.getArgument(0);
          return BrandDTO.builder().id(entity.getId()).name(entity.getName()).build();
        });

    when(brandMapper.newDtoToEntity(ArgumentMatchers.any(NewBrandDTO.class)))
        .thenAnswer(invocation -> {
          NewBrandDTO dto = invocation.getArgument(0);
          return BrandEntity.builder().name(dto.name()).build();
        });

    when(brandMapper.entitiesToDtos(ArgumentMatchers.anyList()))
        .thenAnswer(invocation -> {
          List<BrandEntity> entities = invocation.getArgument(0);
          return entities.stream()
              .map(entity -> BrandDTO.builder().id(entity.getId()).name(entity.getName()).build())
              .toList();
        });

    doAnswer(new Answer<Void>() {
      @Override
      public Void answer(InvocationOnMock invocation) throws Throwable {
        BrandUpdatesDTO updatesDto = invocation.getArgument(0);
        BrandEntity targetEntity = invocation.getArgument(1);
        if (updatesDto.name() != null) {
          targetEntity.setName(updatesDto.name());
        }
        return null;
      }
    }).when(brandMapper).updateEntityFromDto(any(BrandUpdatesDTO.class), any(BrandEntity.class));
  }

  @Test
  @DisplayName("Deve criar uma nova marca com sucesso")
  void testCreateBrandSuccess() {
    // Given
    NewBrandDTO newBrand = new NewBrandDTO(marca1);

    // When
    brandService.createBrand(newBrand);

    // Then
    BrandEntity createdBrand = brandRepo.findByName(marca1);
    assertNotNull(createdBrand);
    assertNotNull(createdBrand.getId());
    assertEquals(marca1, createdBrand.getName());
  }

  @Test
  @DisplayName("Deve reativar uma marca deletada com sucesso")
  void testReactivateBrandSuccess() {
    // Given
    NewBrandDTO newBrand = new NewBrandDTO(marca1);
    BrandEntity deletedBrand = createBrand(marca1);
    deletedBrand.setDeletedAt(LocalDateTime.now());
    brandRepo.save(deletedBrand);

    // When
    brandService.createBrand(newBrand);

    // Then
    BrandEntity reactivatedBrand = brandRepo.findByName(marca1);
    assertNotNull(reactivatedBrand);
    assertNotNull(reactivatedBrand.getId());
    assertEquals(marca1, reactivatedBrand.getName());
    assertEquals(null, reactivatedBrand.getDeletedAt());
  }

  @Test
  @DisplayName("Deve deletar uma marca com sucesso")
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
  @DisplayName("Deve retornar uma lista vazia ao filtrar marcas inexistentes")
  void testFilterBrandsNotFound() {
    // Given
    String filter = "Marca Inexistente";

    // When
    try {
      brandService.filterBrands(filter);
    } catch (Exception e) {
      // Then
      assertInstanceOf(MotorizenException.class, e);
      assertEquals(MotoriZenResponseCodeEnum.BRAND_NOT_FOUND.getResponseCode(), ((MotorizenException) e).getRc());
    }
  }

  @Test
  @DisplayName("Deve retornar uma lista vazia ao buscar todas as marcas e não houver nenhuma")
  void testFindAllBrandsNotFound() {
    // Given
    brandRepo.deleteAll();

    // When
    try {
      brandService.findAllBrands();
    } catch (Exception e) {
      // Then
      assertInstanceOf(MotorizenException.class, e);
      assertEquals(MotoriZenResponseCodeEnum.BRAND_NOT_FOUND.getResponseCode(), ((MotorizenException) e).getRc());
    }
  }

  @Test
  @DisplayName("Deve filtrar as marcas com sucesso")
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
