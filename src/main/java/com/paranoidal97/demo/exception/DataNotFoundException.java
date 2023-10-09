package com.paranoidal97.demo.exception;

import com.paranoidal97.demo.model.MessageDto;
import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

public class DataNotFoundException extends MedicalclinicException{

    public DataNotFoundException(String message) {
        super(new MessageDto(message, HttpStatus.NOT_FOUND, ZonedDateTime.now().toLocalDateTime()));
    }
}
