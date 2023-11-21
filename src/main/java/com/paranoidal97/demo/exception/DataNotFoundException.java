package com.paranoidal97.demo.exception;

import org.springframework.http.HttpStatus;

public class DataNotFoundException extends MedicalClinicException {
    public DataNotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }
}
