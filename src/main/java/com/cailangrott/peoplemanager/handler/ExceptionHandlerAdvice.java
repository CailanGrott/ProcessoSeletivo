package com.cailangrott.peoplemanager.handler;

import com.cailangrott.peoplemanager.dto.ErrorDTO;
import com.cailangrott.peoplemanager.exception.RecursoNaoEncontradoException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;

@RestControllerAdvice
public class ExceptionHandlerAdvice {

    private static final Logger logger = LoggerFactory.getLogger(ExceptionHandlerAdvice.class);

    @ExceptionHandler(RecursoNaoEncontradoException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ErrorDTO> notFoundResourceHandler(RecursoNaoEncontradoException exception) {
        logger.error(exception.getMessage());
        return new ResponseEntity<>(buildError(exception.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NoSuchElementException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ErrorDTO> notFoundResourceHandler(NoSuchElementException exception) {
        logger.error(exception.getMessage());
        return new ResponseEntity<>(buildError(exception.getMessage()), HttpStatus.NOT_FOUND);
    }

    private ErrorDTO buildError(String message) {
        return new ErrorDTO(message, LocalDateTime.now());
    }
}
