package ru.zinin.notebook.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.zinin.notebook.exception.InvalidToken;
import ru.zinin.notebook.model.Notebook;
import ru.zinin.notebook.service.NotebookService;

@RestController
@RequestMapping("api/notebook")
public class NotebookController {

    private final NotebookService notebookService;

    @Autowired
    public NotebookController(NotebookService notebookService) {
        this.notebookService = notebookService;
    }

    @PostMapping("/create")
    @CrossOrigin(methods = RequestMethod.POST)
    public ResponseEntity<Notebook> create(@RequestBody Notebook notebook) throws InvalidToken {
        return notebookService.create(notebook);
    }
}
