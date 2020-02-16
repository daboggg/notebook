package ru.zinin.notebook.controller;

import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.zinin.notebook.exception.InvalidToken;
import ru.zinin.notebook.exception.SomeException;
import ru.zinin.notebook.model.File;
import ru.zinin.notebook.model.Views;
import ru.zinin.notebook.service.FileService;

import javax.servlet.http.HttpServletResponse;
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


    @GetMapping("{fileId}")
    @CrossOrigin(methods = RequestMethod.GET)
    public ResponseEntity<Resource> download(@PathVariable Long fileId) throws IOException, SomeException, InvalidToken {

        return fileService.download(fileId);

    }

    @PutMapping
    @CrossOrigin(methods = RequestMethod.PUT)
    @JsonView(Views.File_id_fileName.class)
    public ResponseEntity<File> changeFileName(@RequestBody File file) throws SomeException, InvalidToken {
        return fileService.changeFileName(file);
    }

    @DeleteMapping("{id}")
    @CrossOrigin(methods = RequestMethod.DELETE)
    @JsonView(Views.File_id_fileName.class)
    public ResponseEntity<File> deleteFiles(@PathVariable Long id) throws SomeException, InvalidToken {
        return fileService.deleteFiles(id);
    }

    @GetMapping("/check")
    @CrossOrigin(methods = RequestMethod.GET)
    public boolean isEmpty(@RequestParam Long noteId) throws InvalidToken {
        return fileService.isEmpty(noteId);
    }

    @DeleteMapping("/all/{noteId}")
    @CrossOrigin(methods = RequestMethod.DELETE)
    @JsonView(Views.File_id_fileName.class)
    public boolean deleteAllByNoteId(@PathVariable Long noteId) throws InvalidToken {
        return fileService.deleteAllByNoteId(noteId);
    }
}
