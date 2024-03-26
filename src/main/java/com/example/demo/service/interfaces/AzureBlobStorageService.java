package com.example.demo.service.interfaces;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface AzureBlobStorageService {


    String uploadFile(MultipartFile file) throws IOException;

//    byte[] downloadFile(String blobName) throws IOException;

    void downloadFile(String blobName, OutputStream outputStream) throws IOException;

    void deleteFile(String blobName);

    String getFileUrl(String fileName);

    String generateBlobUrl(String fileName);

    InputStream streamFileByUrl(String fileUrl, OutputStream outputStream) throws IOException;

    void streamFile(String fileName, OutputStream outputStream) throws IOException;
}
