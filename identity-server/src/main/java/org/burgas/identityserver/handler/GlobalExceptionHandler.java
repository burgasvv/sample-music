package org.burgas.identityserver.handler;

import io.swagger.v3.oas.annotations.Hidden;
import org.burgas.identityserver.exception.AuthorityNotFoundException;
import org.burgas.identityserver.exception.IdentityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Hidden
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AuthorityNotFoundException.class)
    public ResponseEntity<String> handleAuthorityNotFoundException(AuthorityNotFoundException exception) {
        return ResponseEntity
                .status(NOT_FOUND)
                .body(exception.getMessage());
    }

    @ExceptionHandler(IdentityNotFoundException.class)
    public ResponseEntity<String> handleIdentityNotFoundException(IdentityNotFoundException exception) {
        return ResponseEntity
                .status(NOT_FOUND)
                .body(exception.getMessage());
    }
}
