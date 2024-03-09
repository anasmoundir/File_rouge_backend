package com.example.demo.Mapper;

import com.example.demo.dto.EnrollmentDTO;
import com.example.demo.model.Enrollment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface EnrollmentMapper
{
    EnrollmentMapper INSTANCE = Mappers.getMapper(EnrollmentMapper.class);

    EnrollmentDTO enrollmentToEnrollmentDTO(Enrollment enrollment);
}
