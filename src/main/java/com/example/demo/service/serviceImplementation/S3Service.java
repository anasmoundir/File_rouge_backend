package com.example.demo.service.serviceImplementation;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
public class S3Service implements com.example.demo.service.interfaces.S3Service {
    private final AmazonS3 amazonS3;

    @Value("${aws.access.awsAccessKey}")
    private String awsAccessKey;

    @Value("${aws.access.awsSecretKey}")
    private String awsSecretKey;

    @Value("${aws.access.awsRegion}")
    private String awsRegion;

    @Value("${aws.access.awsBucketName}")
    private String bucketName;

    public S3Service() {
        BasicAWSCredentials credentials = new BasicAWSCredentials(awsAccessKey, awsSecretKey);
        this.amazonS3 = AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(awsRegion)
                .build();
    }

    @Override
    public String uploadResourceFile(MultipartFile file) throws IOException {
        return uploadFile(file);
    }

    @Override
    public String uploadFile(MultipartFile file) throws IOException {
        String fileName = generateUniqueFileName(file.getOriginalFilename());
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(file.getSize());
        amazonS3.putObject(new PutObjectRequest(bucketName, fileName, file.getInputStream(), metadata));
        return amazonS3.getUrl(bucketName, fileName).toString();
    }

    private static String generateUniqueFileName(String originalFileName) {
        String uniqueFileName = UUID.randomUUID().toString();
        String fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
        return uniqueFileName + fileExtension;
    }
}
