package com.api.bonobo.advice;

import java.util.Collections;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.api.bonobo.exception.InternalServerErrorException;
import com.api.bonobo.exception.InvalidFieldsException;
import com.api.bonobo.exception.NotFoundException;
import com.api.bonobo.model.ErrorResponse;

@ControllerAdvice
public class GlobalControllerAdvice {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(NotFoundException notFoundException) {
        return ResponseEntity.badRequest()
                .body(new ErrorResponse(HttpStatus.NOT_FOUND.value(), Collections.singletonList(notFoundException.getMessage())));
    }

    @ExceptionHandler(InvalidFieldsException.class)
    public ResponseEntity<ErrorResponse> handleBadRequest(InvalidFieldsException invalidFieldsException) {
        return ResponseEntity.badRequest()
                .body(new ErrorResponse(HttpStatus.BAD_REQUEST.value(), invalidFieldsException.getErrors()));
    }

    @ExceptionHandler(InternalServerErrorException.class)
    public ResponseEntity<ErrorResponse> handleInternalError(InternalServerErrorException internalServerErrorException) {
        return ResponseEntity.internalServerError()
                .body(new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), Collections.singletonList(internalServerErrorException.getMessage())));
    }
}
