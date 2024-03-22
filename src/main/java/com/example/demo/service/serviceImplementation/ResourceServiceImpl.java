package com.example.demo.service.serviceImplementation;

import com.example.demo.model.Course;
import com.example.demo.model.Lesson;
import com.example.demo.model.Resources;
import com.example.demo.Mapper.ResourceMapper;
import com.example.demo.dto.ResourcesDTO;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.CourseRepository;
import com.example.demo.repository.LessonRepository;
import com.example.demo.repository.ResourceRepository;
import com.example.demo.service.interfaces.AzureBlobStorageService;
import com.example.demo.service.interfaces.ResourceService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ResourceServiceImpl implements ResourceService {
    private final ResourceRepository resourceRepository;
    private final ResourceMapper resourceMapper;
    private final LessonRepository lessonRepository;
    private  final CourseRepository courseRepository;
    private final AzureBlobStorageService azureBlobStorageService;


    public ResourceServiceImpl(ResourceRepository resourceRepository, AzureBlobStorageService azureBlobStorageService , ResourceMapper resourceMapper,
                               LessonRepository lessonRepository, CourseRepository courseRepository) {
        this.resourceRepository = resourceRepository;
        this.resourceMapper = resourceMapper;
        this.lessonRepository = lessonRepository;
        this.courseRepository = courseRepository;
        this.azureBlobStorageService = azureBlobStorageService;
    }

    @Override
    public ResourcesDTO createResource(ResourcesDTO resourcesDTO) {
        long lessonId = resourcesDTO.getLessonId();
        long courseId = resourcesDTO.getCourseId();
        Lesson lesson = lessonRepository.findById(lessonId)
                .orElseThrow(() -> new ResourceNotFoundException("Lesson not found with id: " + lessonId));
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found with id: " + courseId));
        Resources resources = resourceMapper.resourceDTOToResource(resourcesDTO);
        resources.setLesson(lesson);
        resources.setCourse(course);
        resources = resourceRepository.save(resources);
        return resourceMapper.resourceToResourceDTO(resources);
    }

    @Override
    public ResourcesDTO uploadResource(MultipartFile file, String title, String description, Long courseId, Long lessonId) throws IOException {
        String fileName = azureBlobStorageService.uploadFile(file);

        ResourcesDTO resourcesDTO = new ResourcesDTO();
        resourcesDTO.setTitle(title);
        resourcesDTO.setDescription(description);
        resourcesDTO.setUrl(fileName);
        resourcesDTO.setCourseId(courseId);
        resourcesDTO.setLessonId(lessonId);

        return createResource(resourcesDTO);
    }


    @Override
    public List<ResourcesDTO> getResourceDTOsByLessonId(Long lessonId) {
        Lesson lesson = lessonRepository.findById(lessonId)
                .orElseThrow(() -> new ResourceNotFoundException("Lesson not found with ID " + lessonId));
        List<Resources> resources = resourceRepository.findByLesson(lesson);
        List<ResourcesDTO> resourceDTOs = resources.stream()
                .map(resourceMapper::resourceToResourceDTO)
                .collect(Collectors.toList());
        return resourceDTOs;
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