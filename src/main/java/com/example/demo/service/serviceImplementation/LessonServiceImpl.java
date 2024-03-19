package com.example.demo.service.serviceImplementation;

import com.example.demo.Mapper.LessonMapper;
import com.example.demo.dto.LessonDTO;
import com.example.demo.exception.LessonNotFoundException;
import com.example.demo.model.Course;
import com.example.demo.model.Lesson;
import com.example.demo.repository.CourseRepository;
import com.example.demo.repository.LessonRepository;
import com.example.demo.service.interfaces.LessonService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LessonServiceImpl implements LessonService {
    private final LessonRepository lessonRepository;
    private final LessonMapper lessonMapper;

    private final CourseRepository courseRepository;

    @Autowired
    public LessonServiceImpl(LessonRepository lessonRepository, LessonMapper lessonMapper,CourseRepository courseRepository) {
        this.lessonRepository = lessonRepository;
        this.lessonMapper = lessonMapper;
        this.courseRepository = courseRepository;
    }


    @Override
    public LessonDTO createLesson(Long courseId, LessonDTO lessonDTO) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new EntityNotFoundException("Course not found with ID: " + courseId));
        Lesson lesson = new Lesson();
        lesson.setTitle(lessonDTO.getTitle());
        lesson.setContent(lessonDTO.getContent());
        lesson.setCourse(course);
        Lesson savedLesson = lessonRepository.save(lesson);

        return lessonMapper.lessonToLessonDTO(savedLesson);
    }

    @Override
    @Transactional(readOnly = true)
    public LessonDTO getLessonById(Long id) {
        Lesson lesson = lessonRepository.findById(id)
                .orElseThrow(() -> new LessonNotFoundException("Lesson not found with id: " + id));
        return lessonMapper.lessonToLessonDTO(lesson);
    }

    @Override
    @Transactional
    public LessonDTO updateLesson(Long id, LessonDTO lessonDTO) {
        Lesson existingLesson = lessonRepository.findById(id)
                .orElseThrow(() -> new LessonNotFoundException("Lesson not found with id: " + id));


        existingLesson.setTitle(lessonDTO.getTitle());
        existingLesson.setContent(lessonDTO.getContent());

        Lesson updatedLesson = lessonRepository.save(existingLesson);
        return lessonMapper.lessonToLessonDTO(updatedLesson);
    }

    @Override
    @Transactional
    public void deleteLesson(Long id) {
        if (!lessonRepository.existsById(id)) {
            throw new LessonNotFoundException("Lesson not found with id: " + id);
        }
        lessonRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<LessonDTO> getAllLessons() {
        List<Lesson> lessons = lessonRepository.findAll();
        return lessons.stream()
                .map(lessonMapper::lessonToLessonDTO)
                .collect(Collectors.toList());
    }


    @Override
    @Transactional(readOnly = true)
    public List<LessonDTO> getLessonsByStudentId(Long studentId) {
        List<Lesson> lessons = lessonRepository.findLessonsByInstructorUserId(studentId);
        return lessons.stream()
                .map(lessonMapper::lessonToLessonDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<LessonDTO> getLessonsByCategoryId(Long categoryId) {
        List<Lesson> lessons = lessonRepository.findLessonsByCategoryId(categoryId);
        return lessons.stream()
                .map(lessonMapper::lessonToLessonDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<LessonDTO> getLessonsByTeacherId(Long teacherId) {
        List<Lesson> lessons = lessonRepository.findLessonsByInstructorUserId(teacherId);
        return lessons.stream()
                .map(lessonMapper::lessonToLessonDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<LessonDTO> getLessonsBySubcategoryId(Long subcategoryId) {
        List<Lesson> lessons = lessonRepository.findLessonsBySubcategoryId(subcategoryId);
        return lessons.stream()
                .map(lessonMapper::lessonToLessonDTO)
                .collect(Collectors.toList());
    }
}
