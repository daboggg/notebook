package ru.zinin.notebook.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class UserNotFound extends Exception {
    private String email;

    public UserNotFound(String email) {
        super(String.format("a user with this email not found: '%s'", email));
    }
}
