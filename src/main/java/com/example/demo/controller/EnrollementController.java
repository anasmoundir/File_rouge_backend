package com.example.demo.controller;
import com.example.demo.dto.EnrollmentDTO;
import com.example.demo.service.interfaces.EnrollmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/enrollement")
public class EnrollementController {
    private final EnrollmentService enrollmentService;

    @Autowired
    public EnrollementController(EnrollmentService enrollmentService) {
        this.enrollmentService = enrollmentService;
    }

    @PostMapping
    public ResponseEntity<EnrollmentDTO> enrollUser(@RequestParam Long courseId) {
        try {
            EnrollmentDTO enrollmentDTO = enrollmentService.enrollUser(courseId);
            return ResponseEntity.status(HttpStatus.CREATED).body(enrollmentDTO);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @DeleteMapping("/{enrollmentId}")
    public ResponseEntity<Void> cancelEnrollment(@PathVariable Long enrollmentId)
    {
        try {
            enrollmentService.cancelEnrollment(enrollmentId);
            return ResponseEntity.status(HttpStatus.ACCEPTED).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}