package com.paranoidal97.demo.model.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Getter
public class MessageDto {
    private final String message;
    private final HttpStatus status;
    private final LocalDateTime timestamp;
}
