package com.sagar.mapper;

import com.sagar.entity.About;
import org.acme.beans.AboutDTO;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "cdi")
public interface AboutMapper extends BaseMapper {

    AboutDTO toDTO(About about);

    About toEntity(AboutDTO aboutDTO);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDTO(AboutDTO aboutDTO, @MappingTarget About about);
}
