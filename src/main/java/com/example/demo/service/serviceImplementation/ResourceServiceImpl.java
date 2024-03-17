package com.example.demo.service.serviceImplementation;

import com.example.demo.model.Resources;
import com.example.demo.Mapper.ResourceMapper;
import com.example.demo.dto.ResourcesDTO;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.ResourceRepository;
import com.example.demo.service.interfaces.ResourceService;
import org.springframework.stereotype.Service;

@Service
public class ResourceServiceImpl implements ResourceService {
    private final ResourceRepository resourceRepository;
    private final ResourceMapper resourceMapper;

    public ResourceServiceImpl(ResourceRepository resourceRepository, ResourceMapper resourceMapper) {
        this.resourceRepository = resourceRepository;
        this.resourceMapper = resourceMapper;
    }

    @Override
    public ResourcesDTO createResource(ResourcesDTO resourcesDTO) {
        Resources resources =  resourceMapper.resourceDTOToResource(resourcesDTO);
        resources = resourceRepository.save(resources);
        return resourceMapper.resourceToResourceDTO(resources);
    }

    @Override
    public ResourcesDTO getResourceById(Long id) {
        Resources resources = resourceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Resource not found with id: " + id));
        return resourceMapper.resourceToResourceDTO(resources);
    }

    @Override
    public ResourcesDTO updateResource(Long id, ResourcesDTO resourcesDTO) {
        Resources existingResources = resourceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Resource not found with id: " + id));

        existingResources.setTitle(resourcesDTO.getTitle());
        existingResources.setDescription(resourcesDTO.getDescription());
        existingResources.setUrl(resourcesDTO.getUrl());

        existingResources = resourceRepository.save(existingResources);
        return resourceMapper.resourceToResourceDTO(existingResources);
    }

    @Override
    public void deleteResource(Long id) {
        resourceRepository.deleteById(id);
    }
}