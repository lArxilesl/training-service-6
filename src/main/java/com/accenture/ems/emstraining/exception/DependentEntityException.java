package com.accenture.ems.emstraining.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class DependentEntityException extends RuntimeException {
    public DependentEntityException(String message) {
        super(message);
    }
}