package ru.zinin.notebook.model;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "usr")
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @JsonView(Views.UsernameToken.class)
    private String username;
    private String password;
    private String email;
    private Long creationTime;
    @Transient
    @JsonView(Views.UsernameToken.class)
    private String token;
}
