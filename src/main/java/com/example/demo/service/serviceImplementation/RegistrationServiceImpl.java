    package com.example.demo.service.serviceImplementation;

    import com.example.demo.Mapper.CourseMapper;
    import com.example.demo.Mapper.TeacherMapper;
    import com.example.demo.dto.SignUpDTO;
    import com.example.demo.dto.TeacherDTO;
    import com.example.demo.dto.UserTeacherRequest;
    import com.example.demo.model.Teacher;
    import com.example.demo.model.User;
    import com.example.demo.model.UserRole;
    import com.example.demo.repository.TeacherRepository;
    import com.example.demo.repository.UserRepository;
    import com.example.demo.repository.UserRoleRepository;
    import com.example.demo.service.interfaces.RegistrationService;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.security.crypto.password.PasswordEncoder;
    import org.springframework.stereotype.Service;
    import org.springframework.transaction.annotation.Transactional;

    import static com.example.demo.security.config.SecurityConfig.passwordEncoder;

    @Service
    public class RegistrationServiceImpl implements RegistrationService {
        private final UserRepository userRepository;
        private final TeacherRepository teacherRepository;
        private final UserRoleRepository userRoleRepository;
        private final TeacherMapper teacherMapper;
        private final CourseMapper courseMapper;
        @Autowired
        private PasswordEncoder passwordEncoder;


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
            if (userRepository.existsByUsername(signUpDTO.getUsername())) {
                throw new RuntimeException("Username already exists");
            }

            User user = new User();
            user.setUsername(signUpDTO.getUsername());
            user.setEmail(signUpDTO.getEmail());
            user.setUserRole(userRole);
            user.setPassword(passwordEncoder.encode(signUpDTO.getPassword()));
            User savedUser = userRepository.save(user);
            if (userRole.getRoleName().equals("TEACHER")) {
                Teacher teacher = new Teacher();
                teacher.setUser(savedUser);
                teacherRepository.save(teacher);
            }

            return savedUser.getUserId();
        }

        @Override
        public boolean activateUser(String username) {
            User user = userRepository.findByUsername(username);
            if (user == null) {
                return false;
            } else {
                user.setEnabled(true);
                userRepository.save(user);
                return true;
            }
        }

        @Override
        public boolean isEnable(String username) {
            User user = userRepository.findByUsername(username);
            return user != null && user.isEnabled();
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
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new RuntimeException("User not found"));

            Teacher teacher = teacherMapper.teacherDTOToTeacher(teacherDTO);
            teacher.setUser(user);

            teacherRepository.save(teacher);
        }
    }
