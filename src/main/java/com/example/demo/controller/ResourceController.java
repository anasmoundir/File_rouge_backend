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
        try {
            ResourcesDTO savedResourcesDTO = resourceService.createResource(resourcesDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedResourcesDTO);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResourcesDTO> getResourceById(@PathVariable Long id) {
        try {
            ResourcesDTO resourcesDTO = resourceService.getResourceById(id);
            return ResponseEntity.ok(resourcesDTO);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResourcesDTO> updateResource(@PathVariable Long id, @RequestBody ResourcesDTO resourcesDTO) {
        try {
            ResourcesDTO updatedResourcesDTO = resourceService.updateResource(id, resourcesDTO);
            return ResponseEntity.ok(updatedResourcesDTO);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteResource(@PathVariable Long id) {
        try {
            resourceService.deleteResource(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}