package ru.zinin.notebook.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.zinin.notebook.model.Notebook;

public interface NotebookRepo extends JpaRepository<Notebook, Long> {
}
