package com.example.demo.Mapper;

import com.example.demo.dto.CourseDTO;
import com.example.demo.model.Course;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CourseMapper {
    CourseDTO courseToCourseDTO(Course course);

    Course courseDTOToCourse(CourseDTO courseDTO);
}
