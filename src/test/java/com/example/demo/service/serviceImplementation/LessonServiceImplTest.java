package com.example.demo.service.serviceImplementation;
import com.example.demo.Mapper.LessonMapper;
import com.example.demo.dto.LessonDTO;
import com.example.demo.exception.LessonNotFoundException;
import com.example.demo.model.Course;
import com.example.demo.model.Lesson;
import com.example.demo.repository.CourseRepository;
import com.example.demo.repository.LessonRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LessonServiceImplTest {
    @Mock
    private LessonRepository lessonRepository;
    @Mock
    private LessonMapper lessonMapper;
    @Mock
    private CourseRepository courseRepository;

    @InjectMocks
    private LessonServiceImpl lessonService;

    private Lesson lesson;
    private LessonDTO lessonDTO;
    private Course course;

    @BeforeEach
    void setUp() {
        course = new Course();
        course.setCourseId(1L);

        lesson = new Lesson();
        lesson.setLessonId(1L);
        lesson.setTitle("Test Lesson");
        lesson.setContent("Test Content");
        lesson.setCourse(course);

        lessonDTO = new LessonDTO();
        lessonDTO.setLessonId(1L);
        lessonDTO.setTitle("Test LessonDTO");
        lessonDTO.setContent("Test ContentDTO");
    }


    @Test
    void createLesson_Success() {
        when(courseRepository.findById(anyLong())).thenReturn(Optional.of(course));
        when(lessonMapper.lessonToLessonDTO(any(Lesson.class))).thenReturn(lessonDTO);
        when(lessonRepository.save(any(Lesson.class))).thenReturn(lesson);

        LessonDTO createdLesson = lessonService.createLesson(1L, lessonDTO);

        assertNotNull(createdLesson);
        assertEquals(lessonDTO.getTitle(), createdLesson.getTitle());
        verify(courseRepository).findById(anyLong());
        verify(lessonRepository).save(any(Lesson.class));
    }

    @Test
    void getLessonById_Success() {
        when(lessonRepository.findById(anyLong())).thenReturn(Optional.of(lesson));
        when(lessonMapper.lessonToLessonDTO(any(Lesson.class))).thenReturn(lessonDTO);

        LessonDTO foundLesson = lessonService.getLessonById(1L);

        assertNotNull(foundLesson);
        assertEquals(lessonDTO.getTitle(), foundLesson.getTitle());
        verify(lessonRepository).findById(anyLong());
    }

    @Test
    void updateLesson_Success() {
        when(lessonRepository.findById(anyLong())).thenReturn(Optional.of(lesson));
        when(lessonMapper.lessonToLessonDTO(any(Lesson.class))).thenReturn(lessonDTO);
        when(lessonRepository.save(any(Lesson.class))).thenReturn(lesson);

        LessonDTO updatedLesson = lessonService.updateLesson(1L, lessonDTO);

        assertNotNull(updatedLesson);
        assertEquals(lessonDTO.getTitle(), updatedLesson.getTitle());
        verify(lessonRepository).findById(anyLong());
        verify(lessonRepository).save(any(Lesson.class));
    }


    @Test
    void deleteLesson_Success() {
        when(lessonRepository.existsById(anyLong())).thenReturn(true);
        doNothing().when(lessonRepository).deleteById(anyLong());

        assertDoesNotThrow(() -> lessonService.deleteLesson(1L));
        verify(lessonRepository).deleteById(anyLong());
    }

    @Test
    void getLessonsByCourseId_Success() {
        when(lessonRepository.findLessonsByCourseId(anyLong())).thenReturn(Arrays.asList(lesson));
        when(lessonMapper.lessonToLessonDTO(any(Lesson.class))).thenReturn(lessonDTO);

        List<LessonDTO> lessonDTOList = lessonService.getLessonsByCourseId(1L);

        assertFalse(lessonDTOList.isEmpty());
        assertEquals(1, lessonDTOList.size());
        assertEquals(lessonDTO.getTitle(), lessonDTOList.get(0).getTitle());
        verify(lessonRepository).findLessonsByCourseId(anyLong());
    }
    @Test
    void getLessonById_LessonNotFound() {
        when(lessonRepository.findById(anyLong())).thenReturn(Optional.empty());

        Exception exception = assertThrows(LessonNotFoundException.class, () -> lessonService.getLessonById(1L));

        assertTrue(exception.getMessage().contains("Lesson not found with id:"));
        verify(lessonRepository).findById(anyLong());
    }

    @Test
    void createLesson_CourseNotFound() {
        when(courseRepository.findById(anyLong())).thenReturn(Optional.empty());

        Exception exception = assertThrows(EntityNotFoundException.class, () -> lessonService.createLesson(1L, lessonDTO));

        assertTrue(exception.getMessage().contains("Course not found with ID:"));
        verify(courseRepository).findById(anyLong());
    }
    @Test
    void updateLesson_LessonNotFound() {
        when(lessonRepository.findById(anyLong())).thenReturn(Optional.empty());

        Exception exception = assertThrows(LessonNotFoundException.class, () -> lessonService.updateLesson(1L, lessonDTO));

        assertTrue(exception.getMessage().contains("Lesson not found with id:"));
        verify(lessonRepository).findById(anyLong());
    }

    @Test
    void deleteLesson_LessonNotFound() {
        when(lessonRepository.existsById(anyLong())).thenReturn(false);

        Exception exception = assertThrows(LessonNotFoundException.class, () -> lessonService.deleteLesson(1L));

        assertTrue(exception.getMessage().contains("Lesson not found with id:"));
        verify(lessonRepository).existsById(anyLong());
    }

    @Test
    void getLessonsByStudentId_Success() {
        when(lessonRepository.findLessonsByInstructorUserId(anyLong())).thenReturn(Arrays.asList(lesson));
        when(lessonMapper.lessonToLessonDTO(any(Lesson.class))).thenReturn(lessonDTO);

        List<LessonDTO> lessonDTOs = lessonService.getLessonsByStudentId(1L);

        assertFalse(lessonDTOs.isEmpty());
        assertEquals(1, lessonDTOs.size());
        assertEquals(lessonDTO.getTitle(), lessonDTOs.get(0).getTitle());
        verify(lessonRepository).findLessonsByInstructorUserId(anyLong());
    }

    @Test
    void getLessonsByCategoryId_Success() {
        when(lessonRepository.findLessonsByCategoryId(anyLong())).thenReturn(Arrays.asList(lesson));
        when(lessonMapper.lessonToLessonDTO(any(Lesson.class))).thenReturn(lessonDTO);

        List<LessonDTO> lessonDTOs = lessonService.getLessonsByCategoryId(1L);

        assertFalse(lessonDTOs.isEmpty());
        assertEquals(1, lessonDTOs.size());
        assertEquals(lessonDTO.getTitle(), lessonDTOs.get(0).getTitle());
        verify(lessonRepository).findLessonsByCategoryId(anyLong());
    }


}