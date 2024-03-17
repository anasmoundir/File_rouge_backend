package com.example.demo.service.interfaces;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface AzureBlobStorageService {


    String uploadFile(MultipartFile file) throws IOException;

    byte[] downloadFile(String blobName) throws IOException;

    void deleteFile(String blobName);

    String getFileUrl(String fileName);

    String generateBlobUrl(String fileName);
}
