package com.example.demo.service.serviceImplementation;

import com.example.demo.Mapper.TeacherMapper;
import com.example.demo.Mapper.userMapper;
import com.example.demo.dto.SignUpDTO;
import com.example.demo.dto.TeacherDTO;
import com.example.demo.dto.UserDTO;
import com.example.demo.model.Teacher;
import com.example.demo.model.User;
import com.example.demo.model.UserRole;
import com.example.demo.repository.TeacherRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.UserRoleRepository;
import com.example.demo.service.interfaces.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RegistrationServiceImpl implements RegistrationService {
    private final UserRepository userRepository;
    private final TeacherRepository teacherRepository;
    private final UserRoleRepository userRoleRepository;

    @Autowired
    public RegistrationServiceImpl(UserRepository userRepository, TeacherRepository teacherRepository, UserRoleRepository userRoleRepository) {
        this.userRepository = userRepository;
        this.teacherRepository = teacherRepository;
        this.userRoleRepository = userRoleRepository;
    }

    @Override
    @Transactional
    public void register(UserDTO userDTO, TeacherDTO teacherDTO) {
        UserRole userRole = determineUserRole(teacherDTO);
        Long userId = registerUser(userDTO, userRole);
        if (teacherDTO != null) {
            registerTeacher(teacherDTO, userId);
        }
    }


    private UserRole determineUserRole(TeacherDTO teacherDTO) {
        if (teacherDTO != null) {
            return userRoleRepository.findByRoleName("TEACHER");
        } else {
            return userRoleRepository.findByRoleName("STUDENT");
        }
    }

    private Long registerUser(UserDTO userDTO, UserRole userRole) {
        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        user.setUserRole(userRole);
        User savedUser = userRepository.save(user);
        return savedUser.getUserId();
    }

    private void registerTeacher(TeacherDTO teacherDTO, Long userId) {
        teacherDTO.setUserId(userId);
        Teacher teacher = new Teacher();
        // Set teacher attributes
        teacher.setUser(userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found")));
        teacherRepository.save(teacher);
    }

}
