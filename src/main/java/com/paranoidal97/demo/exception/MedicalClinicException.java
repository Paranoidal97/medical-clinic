package com.paranoidal97.demo.exception;

import com.paranoidal97.demo.model.MessageDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public abstract class MedicalClinicException extends RuntimeException {
    private final HttpStatus status;

    public MedicalClinicException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }
}
