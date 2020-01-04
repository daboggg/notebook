package ru.zinin.notebook.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.zinin.notebook.model.Note;

import java.util.List;

public interface NoteRepo extends JpaRepository<Note, Long> {
    List<Note> getAllByNotebookId(Long notebookId);

    Note getById(Long id);
}
