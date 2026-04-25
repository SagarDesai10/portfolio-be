package com.sagar.mapper;

import com.sagar.entity.SocialLink;
import org.acme.beans.SocialDTO;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "cdi")
public interface SocialLinkMapper extends BaseMapper {

    @Mapping(source = "id", target = "id", qualifiedByName = "objectIdToString")
    SocialDTO toDTO(SocialLink socialLink);

    @Mapping(source = "id", target = "id", qualifiedByName = "stringToObjectId")
    SocialLink toEntity(SocialDTO socialDTO);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    void updateEntityFromDTO(SocialDTO socialDTO, @MappingTarget SocialLink socialLink);

    List<SocialDTO> toDTOList(List<SocialLink> socialLinks);
}

