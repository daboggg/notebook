package ru.zinin.notebook.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.zinin.notebook.model.File;

import java.util.List;

public interface FileRepo extends JpaRepository<File, Long> {
    List<File> getAllByNoteId(Long noteId);
}
