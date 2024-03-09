package com.example.demo.Mapper;

import com.example.demo.dto.ResourcesDTO;
import com.example.demo.model.Resources;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface ResourceMapper {
    ResourcesDTO resourceToResourceDTO(Resources resource);
    Resources resourceDTOToResource(ResourcesDTO resourcesDTO);
}
