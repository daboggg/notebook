package ru.zinin.notebook.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_ACCEPTABLE)
public class WrongPassword extends Exception {
    public WrongPassword() {
        super("wrong password");
    }
}
