package com.paranoidal97.demo.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@Builder
@Table
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String email;
    private String password;
    private String idCardNo;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private LocalDate birthday;
    // jeśli usuniemy pacjenta to chcemy usunac wszystkie jego wizyty
    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL)
    private Set<Visit> vistis = new HashSet<>();

    @ManyToMany
    private Set<Doctor> doctors = new HashSet<>();

}
