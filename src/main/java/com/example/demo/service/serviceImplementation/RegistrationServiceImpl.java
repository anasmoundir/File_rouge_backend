    package com.example.demo.service.serviceImplementation;

    import com.example.demo.Mapper.CourseMapper;
    import com.example.demo.Mapper.TeacherMapper;
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
    import com.example.demo.service.interfaces.TeacherService;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.stereotype.Service;
    import org.springframework.transaction.annotation.Transactional;

    @Service
    public class RegistrationServiceImpl implements RegistrationService {
        private final UserRepository userRepository;
        private final TeacherRepository teacherRepository;
        private final UserRoleRepository userRoleRepository;
        private final TeacherMapper teacherMapper;
        private final CourseMapper courseMapper;


        @Autowired
        public RegistrationServiceImpl(UserRepository userRepository, TeacherRepository teacherRepository, UserRoleRepository userRoleRepository, TeacherMapper teacherMapper,
                                       CourseMapper courseMapper) {
            this.userRepository = userRepository;
            this.teacherRepository = teacherRepository;
            this.userRoleRepository = userRoleRepository;
            this.teacherMapper = teacherMapper;
            this.courseMapper = courseMapper;

        }


        @Override
        @Transactional
        public void register(User user) {
            UserRole userRole = determineUserRole(user.getTeacher() != null ? teacherMapper.teacherToTeacherDTO(user.getTeacher()) : null);
            user.setUserRole(userRole);
            User savedUser = userRepository.save(user);
        }

        @Override
        public UserDTO registerUser(SignUpDTO signUpDTO, UserRole userRole) {
            User user = new User();
            user.setUsername(signUpDTO.getUsername());
            user.setEmail(signUpDTO.getEmail());
            user.setPassword(signUpDTO.getPassword());
            user.setUserRole(userRole);
            User savedUser = userRepository.save(user);
            return courseMapper.userToUserDTO(savedUser);
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
        public void registerTeacher(TeacherDTO teacherDTO, UserDTO userDTO) {
            User user = userRepository.findById(userDTO.getUserId())
                    .orElseThrow(() -> new RuntimeException("User not found"));


            teacherDTO.setUser(userDTO);

            Teacher teacher = teacherMapper.teacherDTOToTeacher(teacherDTO);
            teacher.setUser(user);

            teacherRepository.save(teacher);
        }

    }
