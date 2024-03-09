package com.example.demo.controller;

import com.example.demo.dto.ResourceDTO;
import com.example.demo.model.Resource;
import com.example.demo.service.interfaces.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public ResponseEntity<ResourceDTO> createResource(@RequestBody ResourceDTO resourceDTO) {
        ResourceDTO savedResourceDTO = resourceService.createResource(resourceDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedResourceDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResourceDTO> getResourceById(@PathVariable Long id) {
        ResourceDTO resourceDTO = resourceService.getResourceById(id);
        return ResponseEntity.ok(resourceDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResourceDTO> updateResource(@PathVariable Long id, @RequestBody ResourceDTO resourceDTO) {
        ResourceDTO updatedResourceDTO = resourceService.updateResource(id, resourceDTO);
        return ResponseEntity.ok(updatedResourceDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteResource(@PathVariable Long id) {
        resourceService.deleteResource(id);
        return ResponseEntity.noContent().build();
    }
}
