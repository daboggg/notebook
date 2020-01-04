package ru.zinin.notebook.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.zinin.notebook.exception.InvalidToken;
import ru.zinin.notebook.model.Note;
import ru.zinin.notebook.service.NoteService;

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
    public List<Note> getAll(@RequestParam Long notebookId) throws InvalidToken {
        return noteService.getAll(notebookId);
    }

    @PostMapping
    @CrossOrigin(methods = RequestMethod.POST)
    public ResponseEntity<Note> create(@RequestBody Note note) throws InvalidToken {

        return noteService.create(note);
    }

    @PutMapping
    @CrossOrigin(methods = RequestMethod.PUT)
    public ResponseEntity<Note> edit(@RequestBody Note note) throws InvalidToken {
        return noteService.edit(note);
    }
}
