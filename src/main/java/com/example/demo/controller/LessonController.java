package com.example.demo.controller;

import com.example.demo.dto.CourseDTO;
import com.example.demo.dto.LessonDTO;
import com.example.demo.service.interfaces.CourseService;
import com.example.demo.service.interfaces.LessonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/lesson")
public class LessonController {

    private final LessonService lessonService;
    private CourseService courseService;
    @Autowired
    public LessonController(LessonService lessonService, CourseService courseService) {
        this.lessonService = lessonService;
        this.courseService = courseService;
    }


    @PreAuthorize("hasAuthority('TEACHER')")
    @PostMapping("/{courseId}/lessons")
    public ResponseEntity<?> AddLessonTocourse(@PathVariable Long courseId, @RequestBody LessonDTO lessonDTO) {
        try {
            LessonDTO createdLesson = lessonService.createLesson(courseId, lessonDTO);

            Long lessonId = createdLesson.getLessonId();

            return ResponseEntity.status(HttpStatus.CREATED).body(createdLesson);
        } catch (Exception e) {
            String errorMessage = "Error adding lesson to course: " + e.getMessage();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
        }
    }


    @GetMapping("/{id}")
    public ResponseEntity<?> getLessonById(@PathVariable Long id) {
        try {
            LessonDTO lessonDTO = lessonService.getLessonById(id);
            return ResponseEntity.ok(lessonDTO);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Lesson not found with ID: " + id);
        }
    }


    @PreAuthorize("hasAnyAuthority('ADMIN','TEACHER')")
    @PutMapping("/{id}")
    public ResponseEntity<?> updateLesson(@PathVariable Long id, @RequestBody LessonDTO lessonDTO) {
        try {
            LessonDTO updatedLesson = lessonService.updateLesson(id, lessonDTO);
            return ResponseEntity.ok(updatedLesson);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating lesson: " + e.getMessage());
        }
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','TEACHER')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteLesson(@PathVariable Long id) {
        try {
            lessonService.deleteLesson(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error deleting lesson: " + e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<?> getAllLessons() {
        try {
            List<LessonDTO> lessons = lessonService.getAllLessons();
            return ResponseEntity.ok(lessons);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error fetching lessons: " + e.getMessage());
        }
    }

    @GetMapping("/student/{studentId}")
    public ResponseEntity<?> getLessonsByStudentId(@PathVariable Long studentId) {
        try {
            List<LessonDTO> lessons = lessonService.getLessonsByStudentId(studentId);
            return ResponseEntity.ok(lessons);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error fetching lessons for student ID " + studentId + ": " + e.getMessage());
        }
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<?> getLessonsByCategoryId(@PathVariable Long categoryId) {
        try {
            List<LessonDTO> lessons = lessonService.getLessonsByCategoryId(categoryId);
            return ResponseEntity.ok(lessons);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error fetching lessons for category ID " + categoryId + ": " + e.getMessage());
        }
    }


    @GetMapping("/teacher/{teacherId}")
    public ResponseEntity<?> getLessonsByTeacherId(@PathVariable Long teacherId) {
        try {
            List<LessonDTO> lessons = lessonService.getLessonsByTeacherId(teacherId);
            return ResponseEntity.ok(lessons);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error fetching lessons for teacher ID " + teacherId + ": " + e.getMessage());
        }
    }

    @GetMapping("/subcategory/{subcategoryId}")
    public ResponseEntity<?> getLessonsBySubcategoryId(@PathVariable Long subcategoryId) {
        try {
            List<LessonDTO> lessons = lessonService.getLessonsBySubcategoryId(subcategoryId);
            return ResponseEntity.ok(lessons);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error fetching lessons for subcategory ID " + subcategoryId + ": " + e.getMessage());
        }
    }



    @GetMapping("/course/{courseId}")
    public ResponseEntity<?> getLessonsByCourseId(@PathVariable Long courseId) {
        try {
            List<LessonDTO> lessons = lessonService.getLessonsByCourseId(courseId);
            return ResponseEntity.ok(lessons);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error fetching lessons for course ID " + courseId + ": " + e.getMessage());
        }
    }
}