package com.paranoidal97.demo.exception;

import org.springframework.http.HttpStatus;

public class PastAppointmentException extends MedicalClinicException{
    public PastAppointmentException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }

}
