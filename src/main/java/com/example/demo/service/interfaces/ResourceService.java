package com.example.demo.service.interfaces;

import com.example.demo.dto.ResourceDTO;
import com.example.demo.model.Resource;

public interface ResourceService {
    ResourceDTO createResource(ResourceDTO resourceDTO);
    ResourceDTO getResourceById(Long id);
    ResourceDTO updateResource(Long id, ResourceDTO resourceDTO);
    void deleteResource(Long id);
}
