package com.paranoidal97.demo.model.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class DoctorDto {
    private final Long id;
    private final String email;
    private final String name;
    private final String surname;
}
