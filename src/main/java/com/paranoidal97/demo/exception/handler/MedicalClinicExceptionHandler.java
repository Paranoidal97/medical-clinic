package com.paranoidal97.demo.exception.handler;

import com.paranoidal97.demo.exception.DataAlreadyExsistException;
import com.paranoidal97.demo.exception.DataNotFoundException;
import com.paranoidal97.demo.model.MessageDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.http.HttpStatus;
import java.time.ZonedDateTime;

@ControllerAdvice
// To oznacza, że będzie on obsługiwać wyjątki w całej aplikacji.
public class MedicalClinicExceptionHandler  {

    @ExceptionHandler(DataNotFoundException.class)
    public ResponseEntity<MessageDto> handleDataNotFoundexception(DataNotFoundException ex) {
        return ResponseEntity.status(ex.getMessageDto().getStatus()).body(ex.getMessageDto());
    }

    @ExceptionHandler(DataAlreadyExsistException.class)
    public ResponseEntity<MessageDto> handleDataAlreadyExsistException(DataNotFoundException ex) {
        return ResponseEntity.status(ex.getMessageDto().getStatus()).body(ex.getMessageDto());
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<MessageDto> handleRuntimeException(RuntimeException ex) {
        String defaultMessage = "Wystąpił błąd w aplikacji";
        MessageDto messageDto = new MessageDto(defaultMessage, HttpStatus.INTERNAL_SERVER_ERROR, ZonedDateTime.now().toLocalDateTime());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(messageDto);
    }
}
