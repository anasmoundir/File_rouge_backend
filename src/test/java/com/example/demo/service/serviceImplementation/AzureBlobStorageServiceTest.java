package com.example.demo.service.serviceImplementation;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class AzureBlobStorageServiceTest {

    @Mock
    private BlobServiceClient blobServiceClient;

    @Mock
    private BlobContainerClient blobContainerClient;

    @Mock
    private BlobClient blobClient;

    @InjectMocks
    private AzureBlobStorageService azureBlobStorageService;

    @BeforeEach
    void setUp() {
        lenient().when(blobServiceClient.getBlobContainerClient(anyString())).thenReturn(blobContainerClient);
        lenient().when(blobContainerClient.getBlobClient(anyString())).thenReturn(blobClient);
    }



    @Test
    void uploadFile_Success() throws IOException {
        MultipartFile file = new MockMultipartFile("file", "test.txt", "text/plain", "Hello, world!".getBytes());

        azureBlobStorageService.uploadFile(file);

        verify(blobClient).upload(any(ByteArrayInputStream.class), eq(file.getSize()), eq(true));
    }

    @Test
    void downloadFile_Success() throws IOException {
        doAnswer(invocation -> {
            ByteArrayOutputStream outputStream = invocation.getArgument(0);
            outputStream.write("Hello, world!".getBytes());
            return null; // Return null for void methods
        }).when(blobClient).download(any(ByteArrayOutputStream.class));

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        azureBlobStorageService.downloadFile("test.txt", outputStream);

        byte[] content = outputStream.toByteArray();

        assertNotNull(content);
        assertEquals("Hello, world!", new String(content));
    }
    @Test
    void deleteFile_Success() {
        azureBlobStorageService.deleteFile("test.txt");

        verify(blobClient).delete();
    }

    @Test
    void getFileUrl_Success() {
        String url = azureBlobStorageService.getFileUrl("test.txt");

        assertTrue(url.contains("test.txt"));
        assertTrue(url.startsWith("https://filerouge.blob.core.windows.net/rssourceslessons/"));
    }

    @Test
    void getFileUrl_NotFound() {
        String url = azureBlobStorageService.getFileUrl("nonexistent.txt");

        assertTrue(url.contains("nonexistent.txt"));
        assertTrue(url.startsWith("https://filerouge.blob.core.windows.net/rssourceslessons/"));
    }


    @Test
    void uploadFile_EmptyFile() throws IOException {
        MultipartFile emptyFile = new MockMultipartFile("file", "empty.txt", "text/plain", new byte[0]);

        azureBlobStorageService.uploadFile(emptyFile);

        verify(blobClient).upload(any(ByteArrayInputStream.class), eq(0L), eq(true));
    }

    @Test
    void downloadFile_EmptyContent() throws IOException {
        doAnswer(invocation -> {
            ByteArrayOutputStream outputStream = invocation.getArgument(0);
            return null;
        }).when(blobClient).download(any(ByteArrayOutputStream.class));

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        azureBlobStorageService.downloadFile("empty.txt", outputStream);

        assertEquals(0, outputStream.size());
    }

    @Test
    void uploadFile_GeneratesUniqueFileName() throws IOException {
        MultipartFile file = new MockMultipartFile("file", "unique.txt", "text/plain", "Unique Content".getBytes());

        azureBlobStorageService.uploadFile(file);

        verify(blobContainerClient).getBlobClient(argThat(name -> !name.equals("unique.txt")));
    }
    @Test
    void getFileUrl_CorrectFormat() {
        String fileName = "test.txt";
        String expectedUrl = "https://filerouge.blob.core.windows.net/rssourceslessons/" + fileName;

        String url = azureBlobStorageService.getFileUrl(fileName);

        assertEquals(expectedUrl, url);
    }
}