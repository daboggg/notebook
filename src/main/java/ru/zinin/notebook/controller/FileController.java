package ru.zinin.notebook.controller;

import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.zinin.notebook.exception.InvalidToken;
import ru.zinin.notebook.exception.SomeException;
import ru.zinin.notebook.model.Views;
import ru.zinin.notebook.service.FileService;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("/api/file")
public class FileController {

    private final FileService fileService;

    @Autowired
    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @PostMapping
    @CrossOrigin(methods = RequestMethod.POST)
    @JsonView(Views.File_id_fileName.class)
    public List<ru.zinin.notebook.model.File> uploadFile(
            @RequestParam("files") List<MultipartFile> files,
            @RequestParam Long noteId
    ) throws IOException, InvalidToken, SomeException {

        return fileService.uploadFile(files, noteId);
    }

    @GetMapping
    @CrossOrigin(methods = RequestMethod.GET)
    @JsonView(Views.File_id_fileName.class)
    public List<ru.zinin.notebook.model.File> getAll(@RequestParam Long noteId) throws SomeException, InvalidToken {
        return fileService.getAll(noteId);
    }


    @GetMapping("{fileName}")
    @CrossOrigin(methods = RequestMethod.GET)
    public /*ResponseEntity<Resource>*/void download(@PathVariable Long fileName) throws IOException {
        System.out.println(fileName);


        /*File file = new File("/home/ubuntu/store/" + filename);
        ByteArrayResource resource = null;
        if (file.exists()) {
            Path path = Paths.get(file.getAbsolutePath());
            resource = new ByteArrayResource(Files.readAllBytes(path));
        }
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getName() + "\"")
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .contentLength(file.length())
                .body(resource);*/
    }
}
