package com.anck.controllers;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.persistence.EntityNotFoundException;

@ControllerAdvice
public class DefaultControllerAdvice {

    @ExceptionHandler(EntityNotFoundException.class)
    private ResponseEntity handleEntityNotFoundException(EntityNotFoundException e) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(e.getMessage());
    }

    @ExceptionHandler(DataIntegrityViolationException.class) // exc при коллизии shortValue (shortValueUniqConstraint)
    private ResponseEntity handleDataIntegrityViolationException(DataIntegrityViolationException e) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body("Something went wrong, please try it later:)");
    }
}
