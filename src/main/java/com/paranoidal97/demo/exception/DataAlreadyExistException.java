package com.paranoidal97.demo.exception;

import com.paranoidal97.demo.model.MessageDto;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public class DataAlreadyExistException extends MedicalClinicException {
    public DataAlreadyExistException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}
