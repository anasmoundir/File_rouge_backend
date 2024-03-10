package com.example.demo.service.serviceImplementation;
import com.example.demo.Mapper.TeacherMapper;
import com.example.demo.dto.TeacherDTO;
import com.example.demo.exception.TeacherNotFoundException;
import com.example.demo.model.Teacher;
import com.example.demo.repository.TeacherRepository;
import com.example.demo.service.interfaces.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TeacherServiceImpl implements TeacherService {
    private final TeacherRepository teacherRepository;
    private final TeacherMapper teacherMapper;

    @Autowired
    public TeacherServiceImpl(TeacherRepository teacherRepository, TeacherMapper teacherMapper) {
        this.teacherRepository = teacherRepository;
        this.teacherMapper = teacherMapper;
    }

    @Override
    public TeacherDTO validateTeacher(Long teacherId, boolean approved) {
        Teacher teacher = (Teacher) teacherRepository.findById(teacherId)
                .orElseThrow(() -> new TeacherNotFoundException("Teacher not found with id: " + teacherId));
        teacher.setApproved(approved);
        Teacher updatedTeacher = teacherRepository.save(teacher);
        return teacherMapper.teacherToTeacherDTO(updatedTeacher);
    }

    @Override
    public List<TeacherDTO> getAllTeachers() {
        List<Teacher> teachers = teacherRepository.findAll();
        return teachers.stream()
                .map(teacherMapper::teacherToTeacherDTO)
                .collect(Collectors.toList());
    }
}
