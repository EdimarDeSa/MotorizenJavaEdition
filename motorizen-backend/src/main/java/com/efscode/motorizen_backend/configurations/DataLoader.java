package com.efscode.motorizen_backend.configurations;

import java.io.InputStream;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.efscode.motorizen_backend.models.brand.NewBrandDTO;
import com.efscode.motorizen_backend.models.fuel_type.NewFuelTypeDTO;
import com.efscode.motorizen_backend.models.user.NewUser;
import com.efscode.motorizen_backend.models.user.UserEntity;
import com.efscode.motorizen_backend.repositorys.BrandRepository;
import com.efscode.motorizen_backend.repositorys.FuelTypeRepository;
import com.efscode.motorizen_backend.repositorys.UserRepository;
import com.efscode.motorizen_backend.services.BrandService;
import com.efscode.motorizen_backend.services.FuelTypeService;
import com.efscode.motorizen_backend.services.UserService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataLoader implements CommandLineRunner {
  private final UserRepository userRepo;
  private final UserService userService;

  private final BrandRepository brandRepo;
  private final BrandService brandService;

  private final FuelTypeRepository fuelTypeRepo;
  private final FuelTypeService fuelTypeService;

  @Value("${spring.profiles.active}")
  private String mode;

  @Override
  public void run(String... args) throws Exception {
    log.info("Iniciando carregamento de base de dados default!");

    if ("dev".equals(mode))
      loadUsers();

    loadBrands();

    loadFuelTypes();

    log.info("Base de dados carregada com sucesso!");
  }

  private void loadFuelTypes() {
    if (fuelTypeRepo.count() != 0)
      return;

    log.info("Iniciando tipos de combustível...");

    ObjectMapper mapper = new ObjectMapper();
    TypeReference<List<String>> typeReference = new TypeReference<>() {
    };
    InputStream inputStream = TypeReference.class.getResourceAsStream("/defaults/fuel_types.json");

    try {
      List<String> fuelTypes = mapper.readValue(inputStream, typeReference);
      fuelTypes.forEach(fuelType -> {
        NewFuelTypeDTO newFuelType = new NewFuelTypeDTO(fuelType);
        fuelTypeService.createFuelType(newFuelType);
      });
      log.info("Tipos de combustível carregados com sucesso!");
    } catch (Exception e) {
      log.error("Erro ao carregar tipos de combustível", e);
    }
  }

  private void loadBrands() {
    if (brandRepo.count() != 0)
      return;

    log.info("Iniciando marcas...");

    ObjectMapper mapper = new ObjectMapper();
    TypeReference<List<String>> typeReference = new TypeReference<>() {
    };
    InputStream inputStream = TypeReference.class.getResourceAsStream("/defaults/brands.json");

    try {
      List<String> brands = mapper.readValue(inputStream, typeReference);
      brands.forEach(brand -> {
        NewBrandDTO newBrand = new NewBrandDTO(brand);
        brandService.createBrand(newBrand);
      });
      log.info("Marcas carregadas com sucesso!");
    } catch (Exception e) {
      log.error("Erro ao carregar marcas", e);
    }
  }

  private void loadUsers() {
    if (userRepo.count() != 0)
      return;

    log.info("Iniciando usuários...");

    List<NewUser> users = List.of(
        new NewUser(
            "Usuario",
            "de Teste",
            "user@user.com",
            "P4s5W0rD@",
            LocalDate.parse("2000-04-04")),
        new NewUser(
            "Admin",
            "de Teste",
            "admin@admin.com",
            "P4s5W0rD@",
            LocalDate.parse("2000-04-04")));

    users.forEach(user -> {
      log.info("Adicionando usuário {}", user.getFirstName());
      userService.createNewUser(user);
      if ("admin".equals(user.getFirstName())) {
        UserEntity u = userRepo.findByEmail(user.getEmail());
        u.setIsAdministrator(true);
        userRepo.save(u);
      }
    });
  }

}
