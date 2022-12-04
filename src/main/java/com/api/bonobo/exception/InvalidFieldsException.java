package com.api.bonobo.exception;

import java.util.List;

import lombok.Getter;

@Getter
public class InvalidFieldsException extends RuntimeException {
    private final List<String> errors;

    public InvalidFieldsException(List<String> messages) {
        this.errors = messages;
    }

}
