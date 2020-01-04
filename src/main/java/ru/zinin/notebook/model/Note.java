package ru.zinin.notebook.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "note")
@Data
public class Note {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String title;
    private String text;
    private Long creationTime;
    private Long notebookId;
}
