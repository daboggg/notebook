package ru.zinin.notebook.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_ACCEPTABLE)
public class EmailAlreadyExist extends Exception {
    private String email;

    public EmailAlreadyExist(String email) {
        super(String.format("a user with this email already exists: '%s'", email));
    }
}
