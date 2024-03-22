package com.example.demo.Mapper;

import com.example.demo.dto.ResourcesDTO;
import com.example.demo.model.Resources;
import org.mapstruct.Mapper;

import java.util.List;


@Mapper(componentModel = "spring")
public interface ResourceMapper {
    ResourcesDTO resourceToResourceDTO(Resources resource);
    Resources resourceDTOToResource(ResourcesDTO resourcesDTO);
    // map the list of resources to a list of resourcesDTO
    List<ResourcesDTO> resourceToResourceDTO(List<Resources> resources);
    // reverse mapping
    List<Resources> resourceDTOToResource(List<ResourcesDTO> resourcesDTO);
}
