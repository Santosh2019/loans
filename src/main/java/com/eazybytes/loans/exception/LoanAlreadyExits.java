package com.eazybytes.loans.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class LoanAlreadyExits extends RuntimeException {
    public LoanAlreadyExits(String message) {
        super(message);
    }
}
