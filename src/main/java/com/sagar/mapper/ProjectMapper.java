package com.sagar.mapper;

import com.sagar.entity.Project;
import org.acme.beans.ProjectDTO;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "cdi")
public interface ProjectMapper extends BaseMapper {

    @Mapping(source = "id", target = "id", qualifiedByName = "objectIdToString")
    ProjectDTO toDTO(Project project);

    @Mapping(source = "id", target = "id", qualifiedByName = "stringToObjectId")
    Project toEntity(ProjectDTO projectDTO);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    void updateEntityFromDTO(ProjectDTO projectDTO, @MappingTarget Project project);

    List<ProjectDTO> toDTOList(List<Project> projects);
}
