package com.paranoidal97.demo.exception;

import org.springframework.http.HttpStatus;

public class IllegalApointmentTransition extends MedicalClinicException {
    public IllegalApointmentTransition(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}
