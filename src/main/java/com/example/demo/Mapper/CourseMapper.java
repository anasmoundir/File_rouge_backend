package com.example.demo.Mapper;

import com.example.demo.dto.CourseDTO;
import com.example.demo.dto.LessonDTO;
import com.example.demo.dto.ResourceDTO;
import com.example.demo.model.Course;
import com.example.demo.model.Lesson;
import jakarta.annotation.Resource;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CourseMapper {
    CourseDTO courseToCourseDTO(Course course);
    LessonDTO lessonToLessonDTO(Lesson lesson);
    ResourceDTO resourceToResourceDTO(Resource resource);
    
    Resource resourceDTOToResource(ResourceDTO resourceDTO);
}
