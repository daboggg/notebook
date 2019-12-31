package ru.zinin.notebook.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.zinin.notebook.component.TokenFactory;
import ru.zinin.notebook.exception.InvalidToken;
import ru.zinin.notebook.model.Notebook;
import ru.zinin.notebook.repo.NotebookRepo;

@Service
public class NotebookService {

    private final NotebookRepo notebookRepo;
    private final TokenFactory tokenFactory;

    @Autowired
    public NotebookService(NotebookRepo notebookRepo, TokenFactory tokenFactory) {
        this.notebookRepo = notebookRepo;
        this.tokenFactory = tokenFactory;
    }

    public ResponseEntity<Notebook> create(Notebook notebook) throws InvalidToken {
        if (!tokenFactory.isValidToken()) {
            throw new InvalidToken();
        }
        tokenFactory.updateTimeValidityToken();

        notebook.setCreationDate(System.currentTimeMillis());
        Notebook saveNotebook = notebookRepo.save(notebook);
        return ResponseEntity.ok(saveNotebook);
    }
}
