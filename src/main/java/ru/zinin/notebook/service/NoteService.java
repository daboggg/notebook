package ru.zinin.notebook.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.zinin.notebook.component.TokenFactory;
import ru.zinin.notebook.exception.InvalidToken;
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

    public ResponseEntity<Note> create(Note note) throws InvalidToken {

        if (!tokenFactory.isValidToken()) {
            throw new InvalidToken();
        }
        tokenFactory.updateTimeValidityToken();

        note.setCreationTime(System.currentTimeMillis());
        return ResponseEntity.ok(noteRepo.save(note));
    }

    public List<Note> getAll(Long notebookId) throws InvalidToken {

        if (!tokenFactory.isValidToken()) {
            throw new InvalidToken();
        }
        tokenFactory.updateTimeValidityToken();

        return noteRepo.getAllByNotebookId(notebookId);
    }

    public ResponseEntity<Note> edit(Note note) throws InvalidToken {

        if (!tokenFactory.isValidToken()) {
            throw new InvalidToken();
        }
        tokenFactory.updateTimeValidityToken();

        Note noteById = noteRepo.getById(note.getId());
        noteById.setTitle(note.getTitle());
        noteById.setText(note.getText());

        return ResponseEntity.ok(noteRepo.save(noteById));
    }
}
