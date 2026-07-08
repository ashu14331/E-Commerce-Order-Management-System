package com.project.exception.handler;

import com.project.exception.ResourceNotFoundException;
import com.project.exception.ErrorResponce;
import com.project.exception.DuplicateResourceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;


@ControllerAdvice
public class GlobalExceptionHandler{



    @ExceptionHandler(DuplicateResourceException.class)
    public ResponseEntity<ErrorResponce> userIdNotFound(DuplicateResourceException exception) {
        ErrorResponce errorResponce = new ErrorResponce();
        errorResponce.setMessage(exception.getMessage());
        errorResponce.setStatuscode(HttpStatus.NOT_FOUND.value());
        errorResponce.setTimestamp(LocalDateTime.now());
        return new ResponseEntity<>(errorResponce, HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponce> emailAlraedyExit(ResourceNotFoundException exception) {
        ErrorResponce errorResponce = new ErrorResponce();
        errorResponce.setMessage(exception.getMessage());
        errorResponce.setStatuscode(HttpStatus.BAD_REQUEST.value());
        errorResponce.setTimestamp(LocalDateTime.now());
        return new ResponseEntity<>(errorResponce, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handledValidationException(MethodArgumentNotValidException ex){
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            errors.put("error message", error.getDefaultMessage());
        });
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }
}
