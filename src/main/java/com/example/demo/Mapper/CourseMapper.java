package com.example.demo.Mapper;
import com.example.demo.dto.CourseDTO;
import com.example.demo.dto.LessonDTO;
import com.example.demo.dto.ResourcesDTO;
import com.example.demo.dto.UserDTO;
import com.example.demo.model.Course;
import com.example.demo.model.Lesson;
import com.example.demo.model.Resources;
import com.example.demo.model.User;

import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface CourseMapper {
    CourseDTO courseToCourseDTO(Course course);

    Course courseDTOToCourse(CourseDTO courseDTO);

    UserDTO userToUserDTO(User instructor);

    Lesson lessonDTOToLesson(LessonDTO lessonDTO);

    Resources resourceDTOToResource(ResourcesDTO resourceDTO);
}
