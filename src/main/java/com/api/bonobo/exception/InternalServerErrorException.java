package com.api.bonobo.exception;

public class InternalServerErrorException extends RuntimeException {
    public InternalServerErrorException() {
        super("An unexpected error occurred");
    }
}
