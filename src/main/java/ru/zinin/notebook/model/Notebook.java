package ru.zinin.notebook.model;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "notebook")
@Data
public class Notebook {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonView(Views.NotebookIdNotebookName.class)
    private Long id;

    @JsonView(Views.NotebookIdNotebookName.class)
    private String notebookName;
    private Long creationDate;
    private Long userId;

}
