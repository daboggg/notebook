package ru.zinin.notebook.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.zinin.notebook.component.TokenFactory;
import ru.zinin.notebook.exception.InvalidToken;
import ru.zinin.notebook.exception.SomeException;
import ru.zinin.notebook.model.Notebook;
import ru.zinin.notebook.repo.NotebookRepo;

import java.util.List;

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
        notebook.setUserId(tokenFactory.getUserId());
        Notebook saveNotebook = notebookRepo.save(notebook);
        return ResponseEntity.ok(saveNotebook);
    }

    public List<Notebook> getAll() throws InvalidToken {
        if (!tokenFactory.isValidToken()) {
            throw new InvalidToken();
        }
        tokenFactory.updateTimeValidityToken();



        Long userId = tokenFactory.getUserId();
        return notebookRepo.getAllByUserId(userId);
    }

    public ResponseEntity<Notebook> edit(Notebook notebook, String notebookName) throws InvalidToken, SomeException {
        if (!tokenFactory.isValidToken()) {
            throw new InvalidToken();
        }
        tokenFactory.updateTimeValidityToken();

        if (!notebook.getUserId().equals(tokenFactory.getUserId())) {
            throw new SomeException("userId wrong!");
        }

        notebook.setNotebookName(notebookName);
        Notebook notebookFromDb = notebookRepo.save(notebook);
        return ResponseEntity.ok(notebookFromDb);
    }
}
