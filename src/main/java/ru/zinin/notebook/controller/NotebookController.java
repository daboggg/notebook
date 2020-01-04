package ru.zinin.notebook.controller;

import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.zinin.notebook.exception.InvalidToken;
import ru.zinin.notebook.model.Notebook;
import ru.zinin.notebook.model.Views;
import ru.zinin.notebook.service.NotebookService;

import java.util.List;

@RestController
@RequestMapping("api/notebook")
public class NotebookController {

    private final NotebookService notebookService;

    @Autowired
    public NotebookController(NotebookService notebookService) {
        this.notebookService = notebookService;
    }

    @GetMapping()
    @CrossOrigin(methods = RequestMethod.GET)
    @JsonView(Views.NotebookIdNotebookName.class)
    public List<Notebook> getAll() throws InvalidToken {
        return notebookService.getAll();
    }


    @PostMapping()
    @CrossOrigin(methods = RequestMethod.POST)
    @JsonView(Views.NotebookIdNotebookName.class)
    public ResponseEntity<Notebook> create(@RequestBody Notebook notebook) throws InvalidToken {
        return notebookService.create(notebook);
    }

    @PutMapping("/{id}/{notebookName}")
    @CrossOrigin(methods = RequestMethod.PUT)
    @JsonView(Views.NotebookIdNotebookName.class)
    public ResponseEntity<Notebook> edit(
            @PathVariable(name = "id") Notebook notebook,
            @PathVariable String notebookName
            ) throws InvalidToken {
        return notebookService.edit(notebook, notebookName);
    }
}
