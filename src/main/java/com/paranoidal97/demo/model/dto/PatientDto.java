package com.paranoidal97.demo.model.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

@Data
@RequiredArgsConstructor
public class PatientDto {
    private long id;
    private String email;
    private String firstName;
    private String lastName;
    private String phoneNumber;
}
