package ru.zinin.notebook.controller;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@RestController
public class FileController {
    @PostMapping("/upload")
    public String uploadFile(@RequestParam MultipartFile file) throws IOException {
        File saveFile = new File("/home/ubuntu/store/");
        if (!saveFile.exists()) {
            System.out.println(saveFile.mkdir());
        }
        Path path = Paths.get(saveFile.getAbsolutePath() + "/" + file.getOriginalFilename());
        Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
        return "все ок";
    }

    @GetMapping("/download")
    public ResponseEntity<Resource> download(@RequestParam String filename) throws IOException {
        File file = new File("/home/ubuntu/store/" + filename);
        ByteArrayResource resource = null;
        if (file.exists()) {
            Path path = Paths.get(file.getAbsolutePath());
            resource = new ByteArrayResource(Files.readAllBytes(path));
        }
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getName() + "\"")
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .contentLength(file.length())
                .body(resource);
    }
}
