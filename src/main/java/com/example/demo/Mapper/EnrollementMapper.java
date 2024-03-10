package com.example.demo.Mapper;

import com.example.demo.dto.EnrollmentDTO;
import com.example.demo.dto.UserDTO;
import com.example.demo.model.Enrollment;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EnrollementMapper {
    EnrollmentDTO enrollmentToEnrollmentDTO(Enrollment enrollment);
     Enrollment enrollmentDTOToEnrollment(EnrollmentDTO enrollmentDTO);

    UserDTO userToUserDTO(Enrollment enrollment);

}
