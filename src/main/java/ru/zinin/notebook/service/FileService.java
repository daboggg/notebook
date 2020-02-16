package ru.zinin.notebook.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.zinin.notebook.component.TokenFactory;
import ru.zinin.notebook.exception.InvalidToken;
import ru.zinin.notebook.exception.SomeException;
import ru.zinin.notebook.model.File;
import ru.zinin.notebook.model.Note;
import ru.zinin.notebook.repo.FileRepo;
import ru.zinin.notebook.repo.NoteRepo;

import javax.transaction.Transactional;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

@Service
public class FileService {

    @Value(value = "${upload.path}")
    private String partPath;

    private final FileRepo fileRepo;
    private final TokenFactory tokenFactory;
    private final NoteRepo noteRepo;

    @Autowired
    public FileService(FileRepo fileRepo, TokenFactory tokenFactory, NoteRepo noteRepo) {
        this.fileRepo = fileRepo;
        this.tokenFactory = tokenFactory;
        this.noteRepo = noteRepo;
    }

    public List<File> uploadFile(List<MultipartFile> files, Long noteId) throws InvalidToken, SomeException, IOException {

        if (!tokenFactory.isValidToken()) {
            System.out.println(tokenFactory.isValidToken());
            throw new InvalidToken();
        }
        tokenFactory.updateTimeValidityToken();

        Note noteFromDb = noteRepo.getById(noteId);
        if (!noteFromDb.getUserId().equals(tokenFactory.getUserId())) {
            throw new SomeException("userId wrong");
        }

        List<File> fileList = new ArrayList<>();

        for (MultipartFile file : files) {

            File f = new File();
            f.setCreationTime(System.currentTimeMillis());
            f.setFileExtension(getExtentionFile(file.getOriginalFilename()));
            f.setFileName(file.getOriginalFilename());
            f.setNoteId(noteId);
            f.setUserId(tokenFactory.getUserId());

            File saveFile = fileRepo.save(f);
            fileList.add(saveFile);

            try {
                Path storeDir = Paths.get(partPath + saveFile.getId());
                if (!Files.exists(storeDir.getParent())) {
                    Files.createDirectories(storeDir.getParent());
                }
                if (!Files.exists(storeDir)) {
                    Files.createFile(storeDir);
                }
                Files.copy(file.getInputStream(), storeDir, StandardCopyOption.REPLACE_EXISTING);
                System.out.println(storeDir);
            } catch (IOException e) {
                fileRepo.delete(saveFile);
                throw e;
            }
        }
        return fileList;
    }

    private String getExtentionFile(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }

    public List<File> getAll(Long noteId) throws InvalidToken, SomeException {
        if (!tokenFactory.isValidToken()) {
            throw new InvalidToken();
        }
        tokenFactory.updateTimeValidityToken();

        Note noteFromDb = noteRepo.getById(noteId);
        if (!noteFromDb.getUserId().equals(tokenFactory.getUserId())) {
            throw new SomeException("userId wrong");
        }

        return fileRepo.getAllByNoteId(noteId);
    }


    public ResponseEntity<Resource> download(Long fileId) throws InvalidToken, SomeException, IOException {
        if (!tokenFactory.isValidToken()) {
            throw new InvalidToken();
        }
        tokenFactory.updateTimeValidityToken();

        File fileFromDb = fileRepo.getOne(fileId);
        if (!fileFromDb.getUserId().equals(tokenFactory.getUserId())) {
            throw new SomeException("userId wrong");
        }

        java.io.File file = new java.io.File(partPath + fileId);
        ByteArrayResource resource = null;
        if (file.exists()) {
            Path path = Paths.get(file.getAbsolutePath());
            resource = new ByteArrayResource(Files.readAllBytes(path));
        }
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + fileFromDb.getFileName())
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .contentLength(file.length())
                .body(resource);
    }

    public ResponseEntity<File> changeFileName(File file) throws InvalidToken, SomeException {
        if (!tokenFactory.isValidToken()) {
            throw new InvalidToken();
        }
        tokenFactory.updateTimeValidityToken();

        File fileFromDb = fileRepo.getOne(file.getId());
        if (!fileFromDb.getUserId().equals(tokenFactory.getUserId())) {
            throw new SomeException("userId wrong");
        }
        fileFromDb.setFileName(file.getFileName());
        fileFromDb.setFileExtension(getExtentionFile(file.getFileName()));
        return ResponseEntity.ok(fileRepo.save(fileFromDb));
    }

    public ResponseEntity<File> deleteFiles(Long id) throws InvalidToken, SomeException {
        if (!tokenFactory.isValidToken()) {
            throw new InvalidToken();
        }
        tokenFactory.updateTimeValidityToken();

        File fileFromDb = fileRepo.getOne(id);
        if (!fileFromDb.getUserId().equals(tokenFactory.getUserId())) {
            throw new SomeException("userId wrong");
        }

        java.io.File delFile = new java.io.File(partPath + id);
        if (delFile.exists()) {
            delFile.delete();
        }

        fileRepo.delete(fileFromDb);
        return ResponseEntity.ok(fileFromDb);
    }

    public boolean isEmpty(Long noteId) throws InvalidToken {
        return fileRepo.existsByNoteId(noteId);
    }

    @Transactional
    public boolean deleteAllByNoteId(Long noteId) throws InvalidToken {
        if (!tokenFactory.isValidToken()) {
            throw new InvalidToken();
        }
        tokenFactory.updateTimeValidityToken();

        List<File> allFilesByNoteId = fileRepo.getAllByNoteId(noteId);
        for (File file : allFilesByNoteId) {
            java.io.File delFile = new java.io.File(partPath + file.getId());
            if (delFile.exists()) {
                delFile.delete();
            }
        }
        fileRepo.deleteAllByNoteId(noteId);

        return true;
    }
}
