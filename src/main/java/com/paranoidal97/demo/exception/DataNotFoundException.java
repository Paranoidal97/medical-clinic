package com.paranoidal97.demo.exception;

import com.paranoidal97.demo.model.MessageDto;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public class DataNotFoundException extends MedicalClinicException {

    public DataNotFoundException(String message) {
        super(message,HttpStatus.NOT_FOUND);
    }
}
