package com.example.demo.controller;

import com.example.demo.dto.ResourcesDTO;
import com.example.demo.service.interfaces.ResourceService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/resources")
public class ResourceController {
    private final ResourceService resourceService;

    public ResourceController(ResourceService resourceService) {
        this.resourceService = resourceService;
    }

    @PostMapping
    public ResponseEntity<ResourcesDTO> createResource(@RequestBody ResourcesDTO resourcesDTO) {
        ResourcesDTO savedResourcesDTO = resourceService.createResource(resourcesDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedResourcesDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResourcesDTO> getResourceById(@PathVariable Long id) {
        ResourcesDTO resourcesDTO = resourceService.getResourceById(id);
        return ResponseEntity.ok(resourcesDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResourcesDTO> updateResource(@PathVariable Long id, @RequestBody ResourcesDTO resourcesDTO) {
        ResourcesDTO updatedResourcesDTO = resourceService.updateResource(id, resourcesDTO);
        return ResponseEntity.ok(updatedResourcesDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteResource(@PathVariable Long id) {
        resourceService.deleteResource(id);
        return ResponseEntity.noContent().build();
    }
}
