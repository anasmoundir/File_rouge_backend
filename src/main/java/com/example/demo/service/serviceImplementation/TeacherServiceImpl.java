package com.example.demo.service.serviceImplementation;
import com.example.demo.Mapper.TeacherMapper;
import com.example.demo.dto.TeacherDTO;
import com.example.demo.dto.TeacherRegistrationDTO;
import com.example.demo.exception.TeacherNotFoundException;
import com.example.demo.model.Teacher;
import com.example.demo.model.User;
import com.example.demo.model.UserDetail;
import com.example.demo.model.UserRole;
import com.example.demo.repository.TeacherRepository;
import com.example.demo.repository.UserDetailRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.UserRoleRepository;
import com.example.demo.service.interfaces.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class TeacherServiceImpl implements TeacherService {
    private final TeacherRepository teacherRepository;
    private final TeacherMapper teacherMapper;
    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;
    private final UserDetailRepository userDetailRepository;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public TeacherServiceImpl(TeacherRepository teacherRepository, TeacherMapper teacherMapper, UserRepository userRepository, UserRoleRepository userRoleRepository, UserDetailRepository userDetailRepository, PasswordEncoder passwordEncoder) {
        this.teacherRepository = teacherRepository;
        this.teacherMapper = teacherMapper;
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
        this.userDetailRepository = userDetailRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<Teacher> getAllTeachers() {
        return teacherRepository.findAll();
    }

    @Override
    @Transactional
    public TeacherDTO approveTeacher(Long teacherId) {
        Optional<Teacher> optionalTeacher = teacherRepository.findById(teacherId);
        if (optionalTeacher.isPresent()) {
            Teacher teacher = optionalTeacher.get();
            teacher.setApproved(true);
            return teacherMapper.teacherToTeacherDTO(teacherRepository.save(teacher));
        } else {
            throw new TeacherNotFoundException("Teacher not found with id: " + teacherId);
        }
    }




    @Override
    @Transactional
    public void registerTeacher(TeacherRegistrationDTO registrationDTO) {
        User user = new User();
        user.setUsername(registrationDTO.getUsername());
        user.setPassword(passwordEncoder.encode(registrationDTO.getPassword()));
        user.setEmail(registrationDTO.getEmail());
        user.setEnabled(true);

        UserRole teacherRole = userRoleRepository.findByRoleName("TEACHER");
        if (teacherRole == null) {
            teacherRole = new UserRole();
            teacherRole.setRoleName("TEACHER");
            userRoleRepository.save(teacherRole);
        }
        user.setUserRole(teacherRole);

        userRepository.save(user);

        UserDetail userDetail = new UserDetail();
        userDetail.setFirstName(registrationDTO.getFirstName());
        userDetail.setLastName(registrationDTO.getLastName());
        userDetail.setAddress(registrationDTO.getAddress());
        userDetail.setPhoneNumber(registrationDTO.getPhoneNumber());
        userDetail.setUser(user);

        userDetailRepository.save(userDetail);

        Teacher teacher = new Teacher();
        teacher.setUser(user);
        teacher.setApproved(false);
        teacher.setEducationalQualifications(registrationDTO.getEducationalQualifications());
        teacher.setTeachingExperience(registrationDTO.getTeachingExperience());
        teacher.setProfessionalExperience(registrationDTO.getProfessionalExperience());
        teacher.setTeachingPhilosophy(registrationDTO.getTeachingPhilosophy());
        teacher.setReferences(registrationDTO.getReferences());
        teacher.setSampleLessonPlans(registrationDTO.getSampleLessonPlans());
        teacher.setAvailability(registrationDTO.getAvailability());

        teacherRepository.save(teacher);
    }


}
