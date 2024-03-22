package com.example.demo.service.interfaces;

import com.example.demo.dto.ResourcesDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ResourceService {


    ResourcesDTO createResource(ResourcesDTO resourcesDTO);


    ResourcesDTO uploadResource(MultipartFile file, String title, String description, Long courseId, Long lessonId) throws IOException;

    List<ResourcesDTO> getResourceDTOsByLessonId(Long lessonId);

    ResourcesDTO getResourceById(Long id);
    ResourcesDTO updateResource(Long id, ResourcesDTO resourcesDTO);
    void deleteResource(Long id);
}
