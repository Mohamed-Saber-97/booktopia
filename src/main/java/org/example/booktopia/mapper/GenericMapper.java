package org.example.booktopia.mapper;

import java.util.List;
import java.util.Set;

public interface GenericMapper<Entity, DTO> {
    Entity toEntity(DTO dto);

    DTO toDTO(Entity entity);

    List<Entity> toEntities(List<DTO> dtos);

    List<DTO> toDTOs(List<Entity> entities);

    Set<Entity> toEntities(Set<DTO> dtos);

    Set<DTO> toDTOs(Set<Entity> entities);
}
