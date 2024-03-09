package com.example.demo.service.serviceImplementation;

import com.example.demo.Mapper.ResourceMapper;
import com.example.demo.dto.ResourceDTO;
import com.example.demo.model.Resource;
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
    public ResourceDTO createResource(ResourceDTO resourceDTO) {
        Resource resource = resourceMapper.resourceDTOToResource(resourceDTO);
        resource = resourceRepository.save(resource);
        return resourceMapper.resourceToResourceDTO(resource);
    }

    @Override
    public ResourceDTO getResourceById(Long id) {
        Resource resource = resourceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Resource not found with id: " + id));
        return resourceMapper.resourceToResourceDTO(resource);
    }

    @Override
    public ResourceDTO updateResource(Long id, ResourceDTO resourceDTO) {
        Resource existingResource = resourceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Resource not found with id: " + id));

        // Update existing resource with new details
        existingResource.setTitle(resourceDTO.getTitle());
        existingResource.setDescription(resourceDTO.getDescription());
        existingResource.setUrl(resourceDTO.getUrl());

        existingResource = resourceRepository.save(existingResource);
        return resourceMapper.resourceToResourceDTO(existingResource);
    }

    @Override
    public void deleteResource(Long id) {
        resourceRepository.deleteById(id);
    }
}
