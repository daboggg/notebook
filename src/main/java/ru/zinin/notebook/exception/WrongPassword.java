package ru.zinin.notebook.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_ACCEPTABLE)
public class WrongPassword extends Exception {
    private String password;

    public WrongPassword(String password) {
        super(String.format("wrong password: '%s'", password));
    }
}
