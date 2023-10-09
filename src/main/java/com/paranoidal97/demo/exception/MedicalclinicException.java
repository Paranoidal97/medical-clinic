package com.paranoidal97.demo.exception;

import com.paranoidal97.demo.model.MessageDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public class MedicalclinicException extends RuntimeException{
    private final MessageDto messageDto;
}
