package com.sagar.mapper;

import com.sagar.entity.Certificate;
import org.acme.beans.CertificateDTO;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "cdi")
public interface CertificateMapper extends BaseMapper {

    @Mapping(source = "id", target = "id", qualifiedByName = "objectIdToString")
    CertificateDTO toDTO(Certificate certificate);

    @Mapping(source = "id", target = "id", qualifiedByName = "stringToObjectId")
    Certificate toEntity(CertificateDTO certificateDTO);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    void updateEntityFromDTO(CertificateDTO certificateDTO, @MappingTarget Certificate certificate);

    List<CertificateDTO> toDTOList(List<Certificate> certificates);
}
