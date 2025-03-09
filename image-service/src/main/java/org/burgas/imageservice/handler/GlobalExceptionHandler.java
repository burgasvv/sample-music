package org.burgas.imageservice.handler;

import io.swagger.v3.oas.annotations.Hidden;
import org.burgas.imageservice.exception.IdentityNotAuthorizedException;
import org.burgas.imageservice.exception.ImageNotFoundException;
import org.burgas.imageservice.exception.WrongContentTypeException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.MediaType.TEXT_PLAIN;

@Hidden
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ImageNotFoundException.class)
    public ResponseEntity<String> handleImageNotFoundException(ImageNotFoundException exception) {
        return ResponseEntity
                .status(NOT_FOUND)
                .contentType(new MediaType(TEXT_PLAIN, UTF_8))
                .body(exception.getMessage());
    }

    @ExceptionHandler(IdentityNotAuthorizedException.class)
    public ResponseEntity<String> handleIdentityNotAuthorizedException(IdentityNotAuthorizedException exception) {
        return ResponseEntity
                .status(UNAUTHORIZED)
                .contentType(new MediaType(TEXT_PLAIN, UTF_8))
                .body(exception.getMessage());
    }

    @ExceptionHandler(WrongContentTypeException.class)
    public ResponseEntity<String> handleWrongContentTypeException(WrongContentTypeException exception) {
        return ResponseEntity
                .status(NOT_ACCEPTABLE)
                .contentType(new MediaType(TEXT_PLAIN, UTF_8))
                .body(exception.getMessage());
    }
}
