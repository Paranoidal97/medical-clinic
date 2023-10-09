package com.paranoidal97.demo.exception;

import com.paranoidal97.demo.model.MessageDto;
import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

public class DataAlreadyExsistException extends MedicalclinicException{
    public DataAlreadyExsistException(String message) {
        super(new MessageDto(message, HttpStatus.BAD_REQUEST, ZonedDateTime.now().toLocalDateTime()));
    }
}
