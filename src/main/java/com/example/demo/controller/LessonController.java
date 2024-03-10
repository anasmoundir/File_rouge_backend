package com.example.demo.controller;

import com.example.demo.dto.LessonDTO;
import com.example.demo.service.interfaces.LessonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/lesson")
public class LessonController
{
    private final LessonService lessonService;

    @Autowired
    public LessonController(LessonService lessonService) {
        this.lessonService = lessonService;
    }

    @PostMapping
    public LessonDTO createLesson(@RequestBody LessonDTO lessonDTO) {
        return lessonService.createLesson(lessonDTO);
    }

    @GetMapping("/{id}")
    public LessonDTO getLessonById(@PathVariable Long id) {
        return lessonService.getLessonById(id);
    }

    @PutMapping("/{id}")
    public LessonDTO updateLesson(@PathVariable Long id, @RequestBody LessonDTO lessonDTO) {
        return lessonService.updateLesson(id, lessonDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteLesson(@PathVariable Long id) {
        lessonService.deleteLesson(id);
    }

    @GetMapping
    public List<LessonDTO> getAllLessons() {
        return lessonService.getAllLessons();
    }

    @GetMapping("/student/{studentId}")
    public List<LessonDTO> getLessonsByStudentId(@PathVariable Long studentId) {
        return lessonService.getLessonsByStudentId(studentId);
    }

    @GetMapping("/category/{categoryId}")
    public List<LessonDTO> getLessonsByCategoryId(@PathVariable Long categoryId) {
        return lessonService.getLessonsByCategoryId(categoryId);
    }

    @GetMapping("/teacher/{teacherId}")
    public List<LessonDTO> getLessonsByTeacherId(@PathVariable Long teacherId) {
        return lessonService.getLessonsByTeacherId(teacherId);
    }

    @GetMapping("/subcategory/{subcategoryId}")
    public List<LessonDTO> getLessonsBySubcategoryId(@PathVariable Long subcategoryId) {
        return lessonService.getLessonsBySubcategoryId(subcategoryId);
    }

}
