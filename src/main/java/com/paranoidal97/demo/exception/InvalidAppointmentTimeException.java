package com.paranoidal97.demo.exception;

import org.springframework.http.HttpStatus;

public class InvalidAppointmentTimeException extends MedicalClinicException{
    public InvalidAppointmentTimeException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}
