package com.example.demo.service.serviceImplementation;

import com.example.demo.Mapper.TeacherMapper;
import com.example.demo.dto.SignUpDTO;
import com.example.demo.dto.TeacherDTO;
import com.example.demo.dto.UserDTO;
import com.example.demo.dto.UserTeacherRequest;
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
    private final TeacherMapper teacherMapper;
    @Autowired
    public RegistrationServiceImpl(UserRepository userRepository, TeacherRepository teacherRepository, UserRoleRepository userRoleRepository, TeacherMapper teacherMapper) {
        this.userRepository = userRepository;
        this.teacherRepository = teacherRepository;
        this.userRoleRepository = userRoleRepository;
        this.teacherMapper = teacherMapper;
    }




    @Override
    @Transactional
    public void register(UserTeacherRequest request) {
        SignUpDTO signUpDTO = request.getUser();
        TeacherDTO teacherDTO = request.getTeacher();
        UserRole userRole = determineUserRole(teacherDTO);
        Long userId = registerUser(signUpDTO, userRole);

        if (teacherDTO != null) {
            registerTeacher(teacherDTO, userId);
        }
    }

    @Override
    public Long registerUser(SignUpDTO signUpDTO, UserRole userRole) {
        User user = new User();
        user.setUsername(signUpDTO.getUsername());
        user.setEmail(signUpDTO.getEmail());
        user.setPassword(signUpDTO.getPassword());
        user.setUserRole(userRole);
        User savedUser = userRepository.save(user);
        return savedUser.getUserId();
    }
    @Override
    public UserRole determineUserRole(TeacherDTO teacherDTO) {
        if (teacherDTO != null) {
            return userRoleRepository.findByRoleName("TEACHER");
        } else {
            return userRoleRepository.findByRoleName("STUDENT");
        }
    }

    @Override
    public void registerTeacher(TeacherDTO teacherDTO, Long userId) {

    }


}
