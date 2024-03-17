package com.example.demo.controller;

import com.example.demo.dto.ResourcesDTO;
import com.example.demo.service.interfaces.AzureBlobStorageService;
import com.example.demo.service.interfaces.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

@RestController
@RequestMapping("api/resources")
public class ResourceController {
    private final ResourceService resourceService;
    private final AzureBlobStorageService azureBlobStorageService;

    @Autowired
    public ResourceController(ResourceService resourceService, AzureBlobStorageService azureBlobStorageService) {
        this.resourceService = resourceService;
        this.azureBlobStorageService = azureBlobStorageService;
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

            String fileUrl = azureBlobStorageService.getFileUrl(resourcesDTO.getUrl());
            resourcesDTO.setUrl(fileUrl);

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



    @PostMapping("/upload")
    public ResponseEntity<ResourcesDTO> uploadResource(@RequestParam("file") MultipartFile file,
                                                       @RequestParam("title") String title,
                                                       @RequestParam("description") String description,
                                                       @RequestParam("courseId") Long courseId,
                                                       @RequestParam("lessonId") Long lessonId) {
        try {
            String fileName = azureBlobStorageService.uploadFile(file);

            String publicUrl = azureBlobStorageService.getFileUrl(fileName);


            ResourcesDTO resourcesDTO = new ResourcesDTO();
            resourcesDTO.setTitle(title);
            resourcesDTO.setDescription(description);
            resourcesDTO.setUrl(publicUrl);
            resourcesDTO.setCourseId(courseId);
            resourcesDTO.setLessonId(lessonId);

            ResourcesDTO savedResourcesDTO = resourceService.createResource(resourcesDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedResourcesDTO);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}