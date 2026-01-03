package com.ch.srpua.exception;

import com.ch.srpua.constants.ErrorMessages;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@RestControllerAdvice
@RequestMapping(produces = "application/vnd.error+json")
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse> handleExceptions(Exception ex) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

        if (ex instanceof UserError userError) {
            if (userError.getCode() > 0) {
                status = HttpStatus.resolve(userError.getCode());
                if (status == null) {
                    status = HttpStatus.BAD_REQUEST;
                }
            } else {
                status = HttpStatus.NOT_ACCEPTABLE;
            }
            log.warn("User error: {}", userError.getMessage());
        } else {
            log.error("Unexpected error occurred", ex);
        }

        String message = ex.getMessage() != null ? ex.getMessage() : ErrorMessages.UNEXPECTED_ERROR;
        return ResponseEntity.status(status).body(ApiResponse.of(status.value(), message));
    }
}

