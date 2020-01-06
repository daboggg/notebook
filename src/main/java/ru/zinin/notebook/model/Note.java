package ru.zinin.notebook.model;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "note")
@Data
public class Note {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonView(Views.IdTitleTextNotebookId.class)
    private Long id;

    @JsonView(Views.IdTitleTextNotebookId.class)
    private String title;

    @JsonView(Views.IdTitleTextNotebookId.class)
    @Column(length = 10000)
    private String text;
    private Long creationTime;
    @JsonView(Views.IdTitleTextNotebookId.class)
    private Long notebookId;
    private Long userId;
}
