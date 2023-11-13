package com.paranoidal97.demo.exception.handler;

import com.paranoidal97.demo.exception.*;
import com.paranoidal97.demo.model.dto.MessageDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;

@ControllerAdvice
public class MedicalClinicExceptionHandler {
    @ExceptionHandler(DataNotFoundException.class)
    public ResponseEntity<MessageDto> handleDataNotFoundException(DataNotFoundException ex) {
        return ResponseEntity.status(ex.getStatus()).body(new MessageDto(ex.getMessage(), ex.getStatus(), LocalDateTime.now()));
    }

    @ExceptionHandler(DataAlreadyExistException.class)
    public ResponseEntity<MessageDto> handleDataAlreadyExsistException(DataNotFoundException ex) {
        return ResponseEntity.status(ex.getStatus()).body(new MessageDto(ex.getMessage(), ex.getStatus(), LocalDateTime.now()));
    }

    @ExceptionHandler(PastAppointmentException.class)
    public ResponseEntity<MessageDto> handlePastAppointmentException(PastAppointmentException ex) {
        return ResponseEntity.status(ex.getStatus()).body(new MessageDto(ex.getMessage(), ex.getStatus(), LocalDateTime.now()));
    }

    @ExceptionHandler(InvalidAppointmentTimeException.class)
    public ResponseEntity<MessageDto> handleInvalidAppointmentTimeException(InvalidAppointmentTimeException ex) {
        return ResponseEntity.status(ex.getStatus()).body(new MessageDto(ex.getMessage(), ex.getStatus(), LocalDateTime.now()));
    }

    @ExceptionHandler(DuplicateAppointmentException.class)
    public ResponseEntity<MessageDto> handleDuplicateAppointmentException(DuplicateAppointmentException ex) {
        return ResponseEntity.status(ex.getStatus()).body(new MessageDto(ex.getMessage(), ex.getStatus(), LocalDateTime.now()));
    }

    @ExceptionHandler(IllegalApointmentTransition.class)
    public ResponseEntity<MessageDto> handleIllegalApointmentTransition(IllegalApointmentTransition ex) {
        return ResponseEntity.status(ex.getStatus()).body(new MessageDto(ex.getMessage(), ex.getStatus(), LocalDateTime.now()));
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<MessageDto> handleBadRequestException(BadRequestException ex) {
        return ResponseEntity.status(ex.getStatus()).body(new MessageDto(ex.getMessage(), ex.getStatus(), LocalDateTime.now()));
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<MessageDto> handleRuntimeException(RuntimeException ex) {
        String defaultMessage = "Error in apliaction";
        MessageDto messageDto = new MessageDto(defaultMessage, HttpStatus.INTERNAL_SERVER_ERROR, ZonedDateTime.now().toLocalDateTime());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(messageDto);
    }


}
