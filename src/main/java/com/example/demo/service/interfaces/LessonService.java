package com.example.demo.service.interfaces;

import com.example.demo.dto.LessonDTO;

import java.util.List;

public interface LessonService {
    LessonDTO createLesson(LessonDTO lessonDTO);
    LessonDTO getLessonById(Long id);
    LessonDTO updateLesson(Long id, LessonDTO lessonDTO);
    void deleteLesson(Long id);
    List<LessonDTO> getAllLessons();
    List<LessonDTO> getLessonsByStudentId(Long studentId);
    List<LessonDTO> getLessonsByCategoryId(Long categoryId);
    List<LessonDTO> getLessonsByTeacherId(Long teacherId);
    List<LessonDTO> getLessonsBySubcategoryId(Long subcategoryId);
}
