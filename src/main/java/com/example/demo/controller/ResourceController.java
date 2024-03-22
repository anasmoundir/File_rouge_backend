    package com.example.demo.controller;

    import com.example.demo.dto.ResourcesDTO;
    import com.example.demo.service.interfaces.AzureBlobStorageService;
    import com.example.demo.service.interfaces.ResourceService;
    import jakarta.servlet.http.HttpServletResponse;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.http.HttpStatus;
    import org.springframework.http.MediaType;
    import org.springframework.http.ResponseEntity;
    import org.springframework.web.bind.annotation.*;
    import org.springframework.web.multipart.MultipartFile;
    import java.io.IOException;
    import java.io.OutputStream;
    import java.net.URLDecoder;
    import java.util.List;

    @RestController
    @RequestMapping("api/resources")
    public class ResourceController {
        private final ResourceService resourceService;
        private final AzureBlobStorageService azureBlobStorageService;

        @Autowired
        public ResourceController(ResourceService resourceService, AzureBlobStorageService azureBlobStorageService) {
            this.resourceService = resourceService;
            this.azureBlobStorageService = azureBlobStorageService;
        }

        @PostMapping
        public ResponseEntity<ResourcesDTO> createResource(@RequestBody ResourcesDTO resourcesDTO) {
            try {
                ResourcesDTO savedResourcesDTO = resourceService.createResource(resourcesDTO);
                return ResponseEntity.status(HttpStatus.CREATED).body(savedResourcesDTO);
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        }

        @GetMapping("/{id}")
        public ResponseEntity<ResourcesDTO> getResourceById(@PathVariable Long id) {
            try {
                ResourcesDTO resourcesDTO = resourceService.getResourceById(id);

                String fileUrl = azureBlobStorageService.getFileUrl(resourcesDTO.getUrl());
                resourcesDTO.setUrl(fileUrl);

                return ResponseEntity.ok(resourcesDTO);
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        }



        @GetMapping("/lesson/{lessonId}")
        public ResponseEntity<List<ResourcesDTO>> getResourcesByLessonId(@PathVariable Long lessonId) {
            List<ResourcesDTO> resourceDTOs = resourceService.getResourceDTOsByLessonId(lessonId);
            return ResponseEntity.ok(resourceDTOs);
        }



        @GetMapping("/stream")
        public void streamFile(@RequestParam("url") String url, HttpServletResponse response) {
            try {
                String decodedUrl = URLDecoder.decode(url, "UTF-8");

                String fileName = extractFileNameFromUrl(decodedUrl);

                response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
                response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");

                OutputStream outputStream = response.getOutputStream();

                azureBlobStorageService.streamFileByUrl(decodedUrl, outputStream);

                outputStream.flush();
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }
        }

        private String extractFileNameFromUrl(String url) {

            String[] parts = url.split("/");
            return parts[parts.length - 1];
        }


        @PutMapping("/{id}")
        public ResponseEntity<ResourcesDTO> updateResource(@PathVariable Long id, @RequestBody ResourcesDTO resourcesDTO) {
            try {
                ResourcesDTO updatedResourcesDTO = resourceService.updateResource(id, resourcesDTO);
                return ResponseEntity.ok(updatedResourcesDTO);
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        }

        @DeleteMapping("/{id}")
        public ResponseEntity<Void> deleteResource(@PathVariable Long id) {
            try {
                resourceService.deleteResource(id);
                return ResponseEntity.noContent().build();
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        }



        @PostMapping(value = "/upload", consumes = MediaType.APPLICATION_JSON_VALUE)
        public ResponseEntity<ResourcesDTO> uploadResource(@RequestParam("file") MultipartFile file,
                                                           @RequestParam("title") String title,
                                                           @RequestParam("description") String description,
                                                           @RequestParam("courseId") Long courseId,
                                                           @RequestParam("lessonId") Long lessonId) {
            try {
                ResourcesDTO savedResourcesDTO = resourceService.uploadResource(file, title, description, courseId, lessonId);
                return ResponseEntity.status(HttpStatus.CREATED).body(savedResourcesDTO);
            } catch (IOException e) {
                e.printStackTrace();
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        }



    }