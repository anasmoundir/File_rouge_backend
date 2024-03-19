package com.example.demo.Mapper;

import com.example.demo.dto.LessonDTO;
import com.example.demo.model.Lesson;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LessonMapper {
    Lesson lessonDTOToLesson(LessonDTO lessonDTO);
    LessonDTO lessonToLessonDTO(Lesson lesson);
}
