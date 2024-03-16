package com.example.demo.service.interfaces;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface S3Service {
    String uploadResourceFile(MultipartFile file) throws IOException;

    String uploadFile(MultipartFile file) throws IOException;
}
