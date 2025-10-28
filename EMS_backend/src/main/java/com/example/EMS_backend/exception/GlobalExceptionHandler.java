package com.example.EMS_backend.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleMethodArgumentNotValid(MethodArgumentNotValidException me){
        logger.error("Validation error occurred", me);
        Map<String, Object> responseData = new HashMap<>();
        Map<String, String> allErrors = new HashMap<>();
        me
                .getBindingResult()
                .getFieldErrors()
                .stream()
                .forEach((eachError) -> allErrors.put(eachError.getField(), eachError.getDefaultMessage()));
        responseData.put("datetime", LocalDateTime.now());
        responseData.put("errors", allErrors);
        return new ResponseEntity<Map<String, Object>>(responseData, HttpStatus.BAD_REQUEST);
    }


}
