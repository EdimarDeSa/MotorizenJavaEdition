package com.efscode.motorizen_backend.configurations;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
public class SwaggerConfiguration {

  @Value("${app.info.name}")
  private String projectName;

  @Value("${app.info.version}")
  private String projectVersion;

  @Value("${app.info.description}")
  private String projectDescription;

  @Bean
  OpenAPI customOpenAPI() {
    return new OpenAPI()
        .info(generateInfo())
        .components(generateComponents())
        .addSecurityItem(new SecurityRequirement().addList("bearerAuth"));
  }

  private Info generateInfo() {
    return new Info()
        .title(projectName)
        .version(projectVersion)
        .description(projectDescription);
  }

  private Components generateComponents() {
    return new Components()
        .addSecuritySchemes("bearerAuth", createSecurityScheme());
  }

  private SecurityScheme createSecurityScheme() {
    return new SecurityScheme()
        .name("bearerAuth")
        .type(SecurityScheme.Type.HTTP)
        .scheme("bearer")
        .bearerFormat("JWT")
        .in(SecurityScheme.In.HEADER);
  }

}
