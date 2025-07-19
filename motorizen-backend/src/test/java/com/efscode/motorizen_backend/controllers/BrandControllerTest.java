package com.efscode.motorizen_backend.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.efscode.motorizen_backend.config.test.TestSecurityConfig;
import com.efscode.motorizen_backend.configurations.TokenAuthFilter;
import com.efscode.motorizen_backend.enums.MotoriZenResponseCodeEnum;
import com.efscode.motorizen_backend.models.brand.BrandDTO;
import com.efscode.motorizen_backend.models.brand.BrandUpdatesDTO;
import com.efscode.motorizen_backend.models.brand.NewBrandDTO;
import com.efscode.motorizen_backend.services.BrandService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(controllers = BrandController.class, excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = TokenAuthFilter.class))
@ContextConfiguration(classes = { TestSecurityConfig.class })
@DisplayName("BrandController Tests")
public class BrandControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockitoBean
  private BrandService brandService;

  @Autowired
  private ObjectMapper objectMapper;

  private BrandDTO generateBrandDTO(Integer id, String name) {
    return BrandDTO.builder().id(id).name(name).build();
  }

  @Test
  @DisplayName("Should return all brands successfully for any user")
  @WithMockUser(username = "user", roles = { "USER" })
  void testGetAllBrandsSuccess() throws Exception {
    BrandDTO brand1 = generateBrandDTO(1, "Toyota");
    BrandDTO brand2 = generateBrandDTO(2, "Honda");
    List<BrandDTO> mockBrands = List.of(brand1, brand2);
    when(brandService.findAllBrands()).thenReturn(mockBrands);

    mockMvc.perform(get("/brand/all")
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.rc").value(MotoriZenResponseCodeEnum.OK.getResponseCode()))
        .andExpect(jsonPath("$.data").isArray())
        .andExpect(jsonPath("$.data[0].id").value(1))
        .andExpect(jsonPath("$.data[0].name").value("Toyota"))
        .andExpect(jsonPath("$.data[1].id").value(2))
        .andExpect(jsonPath("$.data[1].name").value("Honda"));

    verify(brandService, times(1)).findAllBrands();
  }

  @Test
  @DisplayName("Should create a new brand successfully as ADMIN")
  @WithMockUser(username = "admin", roles = { "ADMIN" })
  void testCreateBrandSuccessAsAdmin() throws Exception {
    NewBrandDTO newBrand = new NewBrandDTO("Ford");
    doNothing().when(brandService).createBrand(any(NewBrandDTO.class));

    mockMvc.perform(post("/brand/new")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(newBrand)))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.rc").value(MotoriZenResponseCodeEnum.OK.getResponseCode()))
        .andExpect(jsonPath("$.data").isEmpty());

    verify(brandService, times(1)).createBrand(newBrand);
  }

  @Test
  @DisplayName("Should return forbidden when creating a new brand as USER")
  @WithMockUser(username = "user", roles = { "USER" })
  void testCreateBrandForbiddenAsUser() throws Exception {
    NewBrandDTO newBrand = new NewBrandDTO("Ford");

    mockMvc.perform(post("/brand/new")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(newBrand)))
        .andExpect(status().isUnauthorized())
        .andExpect(jsonPath("$.rc").value(MotoriZenResponseCodeEnum.UNAUTHORIZED_ACCESS.getResponseCode()))
        .andExpect(jsonPath("$.data").isString());

    verifyNoInteractions(brandService);
  }

  @Test
  @DisplayName("Should return forbidden when creating a new brand unauthenticated")
  void testCreateBrandUnauthorized() throws Exception {
    NewBrandDTO newBrand = new NewBrandDTO("Ford");

    mockMvc.perform(post("/brand/new")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(newBrand)))
        .andExpect(status().isForbidden());

    verifyNoInteractions(brandService);
  }

  @Test
  @DisplayName("Should update an existing brand successfully as ADMIN")
  @WithMockUser(username = "admin", roles = { "ADMIN" })
  void testUpdateBrandSuccessAsAdmin() throws Exception {
    Integer brandId = 1;
    String newName = "Gurgel Updated";
    BrandUpdatesDTO brandUpdates = new BrandUpdatesDTO(newName);
    BrandDTO updatedBrandDTO = generateBrandDTO(brandId, newName);

    when(brandService.updateBrand(eq(brandId), any(BrandUpdatesDTO.class)))
        .thenReturn(updatedBrandDTO);

    mockMvc.perform(put("/brand/update/{id}", brandId)
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(brandUpdates)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.rc").value(MotoriZenResponseCodeEnum.OK.getResponseCode()))
        .andExpect(jsonPath("$.data.id").value(brandId))
        .andExpect(jsonPath("$.data.name").value(newName));

    verify(brandService, times(1)).updateBrand(eq(brandId), any(BrandUpdatesDTO.class));
  }

  @Test
  @DisplayName("Should return forbidden when updating a brand as USER")
  @WithMockUser(username = "user", roles = { "USER" })
  void testUpdateBrandForbiddenAsUser() throws Exception {
    Integer brandId = 1;
    String newName = "Gurgel Updated";
    BrandUpdatesDTO brandUpdates = new BrandUpdatesDTO(newName);

    mockMvc.perform(put("/brand/update/{id}", brandId)
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(brandUpdates)))
        .andExpect(status().isUnauthorized())
        .andExpect(jsonPath("$.rc").value(MotoriZenResponseCodeEnum.UNAUTHORIZED_ACCESS.getResponseCode()))
        .andExpect(jsonPath("$.data").isString());

    verifyNoInteractions(brandService);
  }

  @Test
  @DisplayName("Should return forbidden when updating a brand unauthenticated")
  void testUpdateBrandUnauthorized() throws Exception {
    Integer brandId = 1;
    String newName = "Gurgel Updated";
    BrandUpdatesDTO brandUpdates = new BrandUpdatesDTO(newName);

    mockMvc.perform(put("/brand/update/{id}", brandId)
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(brandUpdates)))
        .andExpect(status().isForbidden());

    verifyNoInteractions(brandService);
  }

  @Test
  @DisplayName("Should delete a brand successfully as ADMIN")
  @WithMockUser(username = "admin", roles = { "ADMIN" })
  void testDeleteBrandSuccessAsAdmin() throws Exception {
    Integer brandId = 1;
    doNothing().when(brandService).deleteBrand(brandId);

    mockMvc.perform(delete("/brand/delete/{id}", brandId)
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.rc").value(MotoriZenResponseCodeEnum.OK.getResponseCode()))
        .andExpect(jsonPath("$.data").isEmpty());

    verify(brandService, times(1)).deleteBrand(brandId);
  }

  @Test
  @DisplayName("Should return forbidden when deleting a brand as USER")
  @WithMockUser(username = "user", roles = { "USER" })
  void testDeleteBrandForbiddenAsUser() throws Exception {
    Integer brandId = 1;

    mockMvc.perform(delete("/brand/delete/{id}", brandId)
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isUnauthorized())
        .andExpect(jsonPath("$.rc").value(MotoriZenResponseCodeEnum.UNAUTHORIZED_ACCESS.getResponseCode()))
        .andExpect(jsonPath("$.data").isString());

    verifyNoInteractions(brandService);
  }

  @Test
  @DisplayName("Should return forbidden when deleting a brand unauthenticated")
  void testDeleteBrandUnauthorized() throws Exception {
    Integer brandId = 1;

    mockMvc.perform(delete("/brand/delete/{id}", brandId)
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isForbidden());

    verifyNoInteractions(brandService);
  }
}