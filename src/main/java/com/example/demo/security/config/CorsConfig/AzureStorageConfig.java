package com.example.demo.security.config.CorsConfig;

import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.BlobServiceClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AzureStorageConfig {
    private String connectionString = "DefaultEndpointsProtocol=https;AccountName=filerouge;AccountKey=2+Aop0QOzAzCdY3a1kBsg3NtKC4nz4MRD1+LmjStKeGIRWeSbr1YuhYn3Z6tMs9hrMxIvOcH+Lqy+AStgW1OEg==;EndpointSuffix=core.windows.net";
    @Bean
    public BlobServiceClient blobServiceClient() {
        return new BlobServiceClientBuilder().connectionString(connectionString).buildClient();
    }
}
