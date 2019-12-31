package ru.zinin.notebook.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "notebook")
@Data
public class Notebook {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String notebookName;
    private Long creationDate;

}
