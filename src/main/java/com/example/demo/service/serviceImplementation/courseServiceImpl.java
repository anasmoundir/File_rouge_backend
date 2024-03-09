package com.example.demo.service.serviceImplementation;

import com.example.demo.repository.CourseRepository;
import com.example.demo.service.interfaces.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class courseServiceImpl  implements CourseService {

    @Autowired
    private  final CourseRepository courseRepository;
    @Autowired

    public courseServiceImpl(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }
}
