package com.sagar.mapper;

import com.sagar.entity.Experience;
import org.acme.beans.ExperienceDTO;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "cdi")
public interface ExperienceMapper extends BaseMapper {

    @Mapping(source = "id", target = "id", qualifiedByName = "objectIdToString")
    ExperienceDTO toDTO(Experience experience);

    @Mapping(source = "id", target = "id", qualifiedByName = "stringToObjectId")
    Experience toEntity(ExperienceDTO experienceDTO);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    void updateEntityFromDTO(ExperienceDTO experienceDTO, @MappingTarget Experience experience);

    List<ExperienceDTO> toDTOList(List<Experience> experiences);
}
