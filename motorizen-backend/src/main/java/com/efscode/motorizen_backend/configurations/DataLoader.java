package com.efscode.motorizen_backend.configurations;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.efscode.motorizen_backend.models.dtos.NewUser;
import com.efscode.motorizen_backend.models.entitys.UserEntity;
import com.efscode.motorizen_backend.repositorys.UserRepository;
import com.efscode.motorizen_backend.services.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataLoader implements CommandLineRunner {
  private final UserRepository userRepo;
  private final UserService userService;

  @Value("${spring.profiles.active}")
  private String mode;

  @Override
  public void run(String... args) throws Exception {
    log.info("Iniciando carregamento de base de dados default!");

    if ("dev".equals(mode))
      loadUsers();
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
