package com.paranoidal97.demo.model.dto.doctor;

import com.paranoidal97.demo.model.entity.Patient;
import com.paranoidal97.demo.model.entity.Visit;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Builder
@AllArgsConstructor
public class DoctorDto {
    private Long id;
    private String password;
    private String name;
    private String surname;
    private String email;
    private Set<Patient> patients = new HashSet<>();
    private Set<Visit> visits = new HashSet<>();
}
