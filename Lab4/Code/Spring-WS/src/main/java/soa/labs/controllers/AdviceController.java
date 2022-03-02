package soa.labs.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import soa.labs.exception.*;
import soa.labs.exception.NoSuchPositionException;

import java.util.Objects;

@ControllerAdvice
public class AdviceController {


    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<String> handleMissingParams(MissingServletRequestParameterException ex) {
        String name = ex.getParameterName();
        return new ResponseEntity<>(name + " parameter is missing", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(javax.validation.ConstraintViolationException.class)
    public ResponseEntity<String> handleMissingParams(javax.validation.ConstraintViolationException validationException) {
        return new ResponseEntity<>(validationException.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BindException.class)
    public ResponseEntity<String> handleValidationError(BindException bindException) {
        return new ResponseEntity<>(Objects.requireNonNull(bindException.getFieldError()).getDefaultMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({NoSuchPositionException.class, NoSuchStatusException.class})
    public ResponseEntity<String> handleCustomExceptions(CustomException customException) {
        System.out.println(customException.getCustomMessage());
        return new ResponseEntity<>(customException.getCustomMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoSuchWorkerException.class)
    public ResponseEntity<String> handleNoSuchWorkerException(NoSuchWorkerException noSuchWorkerException) {
        return new ResponseEntity<>(noSuchWorkerException.getMessage(), HttpStatus.NOT_FOUND);
    }

}
