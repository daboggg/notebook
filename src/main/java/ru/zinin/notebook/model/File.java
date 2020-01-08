package ru.zinin.notebook.model;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "file")
@Data
public class File {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonView(Views.File_id_fileName.class)
    private Long id;

    @JsonView(Views.File_id_fileName.class)
    private String fileName;
    private String fileExtension;
    private Long creationTime;
    private Long noteId;
    private Long userId;

}
