package ru.zinin.notebook.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.zinin.notebook.component.TokenFactory;
import ru.zinin.notebook.exception.InvalidToken;
import ru.zinin.notebook.exception.SomeException;
import ru.zinin.notebook.model.File;
import ru.zinin.notebook.model.Note;
import ru.zinin.notebook.repo.FileRepo;
import ru.zinin.notebook.repo.NoteRepo;

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
}
