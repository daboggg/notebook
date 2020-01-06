package ru.zinin.notebook.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_ACCEPTABLE)
public class SomeException extends Exception {
    private String error;

    public SomeException(String error) {
        super(error);
    }
}
