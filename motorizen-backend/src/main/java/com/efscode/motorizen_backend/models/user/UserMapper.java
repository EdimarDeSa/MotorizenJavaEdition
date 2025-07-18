package com.efscode.motorizen_backend.models.user;

import java.util.Set;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

  @Mapping(target = "firstName", source = "dto.firstName", qualifiedByName = "capitalizeName")
  @Mapping(target = "lastName", source = "dto.lastName", qualifiedByName = "capitalizeName")
  @Mapping(target = "email", expression = "java(dto.email().toLowerCase())")
  @Mapping(target = "password", source = "passwordHash")
  @Mapping(target = "isActive", constant = "true")
  @Mapping(target = "isAdministrator", constant = "false")
  UserEntity newDtoToEntity(NewUserDTO dto, String passwordHash);

  @Mapping(target = "firstName", source = "dto.firstName", qualifiedByName = "capitalizeName")
  @Mapping(target = "lastName", source = "dto.lastName", qualifiedByName = "capitalizeName")
  void updateEntityFromDto(UserUpdatesDTO dto, @MappingTarget UserEntity entity);

  @Mapping(target = "createdAt", dateFormat = "dd-MM-yyyy HH:mm:ss")
  @Mapping(target = "updatedAt", dateFormat = "dd-MM-yyyy HH:mm:ss")
  @Mapping(target = "deletedAt", dateFormat = "dd-MM-yyyy HH:mm:ss")
  UserDTO entityToDto(UserEntity entity);

  @Named("capitalizeName")
  default String capitalizeName(String name) {
    String[] names = name.split(" ");
    StringBuilder fullNameBuilder = new StringBuilder();
    Set<String> ignoreWords = Set.of("de", "da", "do", "dos", "das", "e", "a", "o", "os", "as");

    for (String namePiece : names) {
      if (ignoreWords.contains(namePiece.toLowerCase())) {
        namePiece = namePiece.toLowerCase();
      } else {
        namePiece = namePiece.substring(0, 1).toUpperCase() + namePiece.substring(1).toLowerCase();
      }
      fullNameBuilder.append(namePiece);
      fullNameBuilder.append(" ");
    }
    return fullNameBuilder.toString().trim();
  }
}
