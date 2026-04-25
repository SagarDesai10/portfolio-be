package com.sagar.mapper;

import com.sagar.entity.Skill;
import org.acme.beans.SkillDTO;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "cdi")
public interface SkillMapper extends BaseMapper {

    @Mapping(source = "id", target = "id", qualifiedByName = "objectIdToString")
    SkillDTO toDTO(Skill skill);

    @Mapping(source = "id", target = "id", qualifiedByName = "stringToObjectId")
    Skill toEntity(SkillDTO skillDTO);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    void updateEntityFromDTO(SkillDTO skillDTO, @MappingTarget Skill skill);

    List<SkillDTO> toDTOList(List<Skill> skills);
}
