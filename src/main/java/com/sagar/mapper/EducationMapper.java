package com.sagar.mapper;

import com.sagar.entity.Education;
import org.acme.beans.EducationDTO;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "cdi")
public interface EducationMapper extends BaseMapper {

    @Mapping(source = "id", target = "id", qualifiedByName = "objectIdToString")
    EducationDTO toDTO(Education education);

    @Mapping(source = "id", target = "id", qualifiedByName = "stringToObjectId")
    Education toEntity(EducationDTO educationDTO);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    void updateEntityFromDTO(EducationDTO educationDTO, @MappingTarget Education education);

    List<EducationDTO> toDTOList(List<Education> educations);
}
