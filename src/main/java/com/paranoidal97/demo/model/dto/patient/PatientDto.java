package com.paranoidal97.demo.model.dto.patient;

import com.paranoidal97.demo.model.entity.Doctor;
import com.paranoidal97.demo.model.entity.Visit;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Getter
@Builder
@AllArgsConstructor
public class PatientDto {
    private long id;
    private String email;
    private String password;
    private String idCardNo;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private LocalDate birthday;
    private Set<Visit> vistis = new HashSet<>();
    private Set<Doctor> doctors = new HashSet<>();
}
