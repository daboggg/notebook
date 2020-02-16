package ru.zinin.notebook.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.zinin.notebook.component.TokenFactory;
import ru.zinin.notebook.exception.InvalidToken;
import ru.zinin.notebook.exception.SomeException;
import ru.zinin.notebook.model.Note;
import ru.zinin.notebook.repo.NoteRepo;

import java.util.List;

@Service
public class NoteService {

    private final NoteRepo noteRepo;
    private final TokenFactory tokenFactory;

    @Autowired
    public NoteService(NoteRepo noteRepo, TokenFactory tokenFactory) {
        this.noteRepo = noteRepo;
        this.tokenFactory = tokenFactory;
    }

    public List<Note> getAll(Long notebookId) throws InvalidToken, SomeException {

        if (!tokenFactory.isValidToken()) {
            throw new InvalidToken();
        }
        tokenFactory.updateTimeValidityToken();

        List<Note> allByNotebookId = noteRepo.getAllByNotebookId(notebookId);
        Long userId = tokenFactory.getUserId();

        //проверяем на подмену userId
        for (Note note : allByNotebookId) {
            if (!note.getUserId().equals(userId)) {
                throw new SomeException("userId wrong");
            }
        }

        return allByNotebookId;
    }

    public ResponseEntity<Note> create(Note note) throws InvalidToken {

        if (!tokenFactory.isValidToken()) {
            throw new InvalidToken();
        }
        tokenFactory.updateTimeValidityToken();

        note.setCreationTime(System.currentTimeMillis());
        note.setUserId(tokenFactory.getUserId());
        return ResponseEntity.ok(noteRepo.save(note));
    }

    public ResponseEntity<Note> edit(Note note) throws InvalidToken, SomeException {

        if (!tokenFactory.isValidToken()) {
            throw new InvalidToken();
        }
        tokenFactory.updateTimeValidityToken();

        Note noteById = noteRepo.getById(note.getId());

        if (!noteById.getUserId().equals(tokenFactory.getUserId())) {
            throw new SomeException("userId wrong");
        }

        noteById.setTitle(note.getTitle());
        noteById.setText(note.getText());

        return ResponseEntity.ok(noteRepo.save(noteById));
    }

    public ResponseEntity<Note> delete(Long noteId) throws InvalidToken, SomeException {
        if (!tokenFactory.isValidToken()) {
            throw new InvalidToken();
        }
        tokenFactory.updateTimeValidityToken();

        Note noteFromDb = noteRepo.getById(noteId);
        if (!noteFromDb.getUserId().equals(tokenFactory.getUserId())) {
            throw new SomeException("userId wrong");
        }

        noteRepo.delete(noteFromDb);
        return ResponseEntity.ok(noteFromDb);
    }
}
