package com.example.demo.Mapper;
import com.example.demo.dto.CourseDTO;
import com.example.demo.dto.LessonDTO;
import com.example.demo.dto.ResourcesDTO;
import com.example.demo.model.Course;
import com.example.demo.model.Lesson;
import jakarta.annotation.Resource;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CourseMapper {
    CourseDTO courseToCourseDTO(Course course);
    LessonDTO lessonToLessonDTO(Lesson lesson);
    ResourcesDTO resourceToResourceDTO(Resource resource);
    Resource resourceDTOToResource(ResourcesDTO resourcesDTO);
}
