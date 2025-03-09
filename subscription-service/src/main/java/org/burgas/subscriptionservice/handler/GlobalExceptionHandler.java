package org.burgas.subscriptionservice.handler;

import io.swagger.v3.oas.annotations.Hidden;
import org.burgas.subscriptionservice.exception.IdentityNotAuthorizedException;
import org.burgas.subscriptionservice.exception.NotCorrespondDataException;
import org.burgas.subscriptionservice.exception.PlanNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.HttpStatus.*;

@Hidden
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IdentityNotAuthorizedException.class)
    public ResponseEntity<String> handleIdentityNotAuthorizedException(IdentityNotAuthorizedException exception) {
        return ResponseEntity
                .status(UNAUTHORIZED)
                .body(exception.getMessage());
    }

    @ExceptionHandler(NotCorrespondDataException.class)
    public ResponseEntity<String> handleNotCorrespondDataException(NotCorrespondDataException exception) {
        return ResponseEntity
                .status(NOT_ACCEPTABLE)
                .body(exception.getMessage());
    }

    @ExceptionHandler(PlanNotFoundException.class)
    public ResponseEntity<String> handlePlanNotFoundException(PlanNotFoundException exception) {
        return ResponseEntity
                .status(NOT_FOUND)
                .body(exception.getMessage());
    }
}
