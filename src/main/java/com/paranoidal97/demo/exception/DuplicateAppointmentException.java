package com.paranoidal97.demo.exception;

import org.springframework.http.HttpStatus;

public class DuplicateAppointmentException extends MedicalClinicException{
    public DuplicateAppointmentException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}
