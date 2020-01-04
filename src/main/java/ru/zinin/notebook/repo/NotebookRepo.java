package ru.zinin.notebook.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.zinin.notebook.model.Notebook;

import java.util.List;

public interface NotebookRepo extends JpaRepository<Notebook, Long> {
    List<Notebook> getAllByUserId(Long id);
}
