package com.paranoidal97.demo.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@Builder
@Table
public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String password;
    private String name;
    private String surname;
    private String email;

    @OneToMany(mappedBy = "doctors")
    private Set<Patient> patients = new HashSet<>();

    // jeśli usuniemy doktora niekoniecznie chcemy wywalać jego wizyty
    @OneToMany(mappedBy = "doctor", fetch = FetchType.LAZY)
    private Set<Visit> visits = new HashSet<>();

}
