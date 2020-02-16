package ru.zinin.notebook.controller;

import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.zinin.notebook.exception.InvalidToken;
import ru.zinin.notebook.exception.SomeException;
import ru.zinin.notebook.model.Note;
import ru.zinin.notebook.model.Views;
import ru.zinin.notebook.service.NoteService;

import javax.transaction.Transactional;
import java.util.List;

@RestController
@RequestMapping("/api/note")
public class NoteController {

    private final NoteService noteService;

    @Autowired
    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    @GetMapping
    @CrossOrigin(methods = RequestMethod.GET)
    @JsonView(Views.IdTitleTextNotebookId.class)
    public List<Note> getAll(@RequestParam Long notebookId) throws InvalidToken, SomeException {
        return noteService.getAll(notebookId);
    }

    @PostMapping
    @CrossOrigin(methods = RequestMethod.POST)
    @JsonView(Views.IdTitleTextNotebookId.class)
    public ResponseEntity<Note> create(@RequestBody Note note) throws InvalidToken {

        return noteService.create(note);
    }

    @PutMapping
    @CrossOrigin(methods = RequestMethod.PUT)
    @JsonView(Views.IdTitleTextNotebookId.class)
    public ResponseEntity<Note> edit(@RequestBody Note note) throws InvalidToken, SomeException {
        return noteService.edit(note);
    }

    @DeleteMapping("{noteId}")
    @CrossOrigin(methods = RequestMethod.DELETE)
    @JsonView(Views.IdTitleTextNotebookId.class)
    public ResponseEntity<Note> delete(@PathVariable Long noteId) throws SomeException, InvalidToken {
        return noteService.delete(noteId);
    }
}
