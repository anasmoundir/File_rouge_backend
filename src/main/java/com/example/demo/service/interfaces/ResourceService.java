package com.example.demo.service.interfaces;

import com.example.demo.dto.ResourcesDTO;

public interface ResourceService {
    ResourcesDTO createResource(ResourcesDTO resourcesDTO);
    ResourcesDTO getResourceById(Long id);
    ResourcesDTO updateResource(Long id, ResourcesDTO resourcesDTO);
    void deleteResource(Long id);
}
