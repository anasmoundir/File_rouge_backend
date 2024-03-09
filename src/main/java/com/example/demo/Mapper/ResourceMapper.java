package com.example.demo.Mapper;

import com.example.demo.dto.ResourceDTO;
import jakarta.annotation.Resource;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface ResourceMapper {
    ResourceDTO resourceToResourceDTO(Resource resource);
    Resource resourceDTOToResource(ResourceDTO resourceDTO);
}
