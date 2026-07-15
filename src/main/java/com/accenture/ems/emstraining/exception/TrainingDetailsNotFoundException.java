package com.accenture.ems.emstraining.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class TrainingDetailsNotFoundException extends RuntimeException {
    public TrainingDetailsNotFoundException() {
        super("Training details not found");
    }
}
