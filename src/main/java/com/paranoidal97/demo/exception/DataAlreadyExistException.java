package com.paranoidal97.demo.exception;

import org.springframework.http.HttpStatus;

public class DataAlreadyExistException extends MedicalClinicException {
    public DataAlreadyExistException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}
