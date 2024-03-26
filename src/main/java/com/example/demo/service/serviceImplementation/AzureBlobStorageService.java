    package com.example.demo.service.serviceImplementation;


    import com.azure.storage.blob.*;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.stereotype.Service;
    import org.springframework.web.multipart.MultipartFile;

    import java.io.IOException;
    import java.io.InputStream;
    import java.io.OutputStream;
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

//        @Override
//        public byte[] downloadFile(String blobName) throws IOException {
//            BlobClient blobClient = blobServiceClient.getBlobContainerClient(containerName).getBlobClient(blobName);
//            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//            blobClient.download(outputStream);
//            return outputStream.toByteArray();
//        }

        @Override
        public void downloadFile(String blobName, OutputStream outputStream) throws IOException {
            BlobClient blobClient = blobServiceClient.getBlobContainerClient(containerName).getBlobClient(blobName);
            blobClient.download(outputStream); // This writes the content to the provided outputStream
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
            String blobUrl = "https://filerouge.blob.core.windows.net/" + containerName + "/" + fileName;
            System.out.println("Generated Blob URL: " + blobUrl);
            return blobUrl;
        }


        private static String generateUniqueFileName(String originalFileName) {
            String uniqueFileName = UUID.randomUUID().toString();
            String fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
            return uniqueFileName + fileExtension;
        }

        @Override
        public InputStream streamFileByUrl(String fileUrl, OutputStream outputStream) throws IOException {
            BlobClient blobClient = getBlobClientFromUrl(fileUrl);
            return blobClient.openInputStream();
        }
        private BlobClient getBlobClientFromUrl(String fileUrl) {
            String blobName = extractBlobNameFromUrl(fileUrl);
            return blobServiceClient.getBlobContainerClient(containerName).getBlobClient(blobName);
        }


        private String extractBlobNameFromUrl(String fileUrl) {
            String[] parts = fileUrl.split("/");
            return parts[parts.length - 1];
        }

        @Override
        public void streamFile(String fileName, OutputStream outputStream) throws IOException {
            BlobClient blobClient = blobServiceClient.getBlobContainerClient(containerName).getBlobClient(fileName);
            blobClient.download(outputStream);
        }

    }
