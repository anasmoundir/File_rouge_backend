package com.example.demo.service.serviceImplementation;


import com.azure.storage.blob.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.UUID;

@Service
public class AzureBlobStorageService implements com.example.demo.service.interfaces.AzureBlobStorageService{

    @Autowired
    private BlobServiceClient blobServiceClient;

    private String containerName = "rssourceslessons" ;


    @Override
    public String uploadFile(MultipartFile file) throws IOException {
        String fileName = generateUniqueFileName(file.getOriginalFilename());
        BlobClient blobClient = blobServiceClient.getBlobContainerClient(containerName).getBlobClient(fileName);
        blobClient.upload(file.getInputStream(), file.getSize(), true);
        return generateBlobUrl(fileName);
    }

    @Override
    public byte[] downloadFile(String blobName) throws IOException {
        BlobClient blobClient = blobServiceClient.getBlobContainerClient(containerName).getBlobClient(blobName);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        blobClient.download(outputStream);
        return outputStream.toByteArray();
    }

    @Override
    public void deleteFile(String blobName) {
        BlobClient blobClient = blobServiceClient.getBlobContainerClient(containerName).getBlobClient(blobName);
        blobClient.delete();
    }

    @Override
    public String getFileUrl(String fileName) {
        return generateBlobUrl(fileName);
    }

    @Override
    public String generateBlobUrl(String fileName) {
        return blobServiceClient.getBlobContainerClient(containerName).getBlobClient(fileName).getBlobUrl();
    }


    private static String generateUniqueFileName(String originalFileName) {
        String uniqueFileName = UUID.randomUUID().toString();
        String fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
        return uniqueFileName + fileExtension;
    }
}
