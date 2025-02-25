package com.eazybytes.loans.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message, String fieldName, String fieldValue) {
        super(String.format("%s not found with the given input %s: %s", message, fieldName, fieldValue));
    }
}
