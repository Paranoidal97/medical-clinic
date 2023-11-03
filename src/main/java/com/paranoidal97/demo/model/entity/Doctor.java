package com.paranoidal97.demo.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
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

    @ManyToMany
    @JoinTable(
            name = "doctor_patient",
            joinColumns = @JoinColumn(name="doctor_id"),
            inverseJoinColumns = @JoinColumn(name="patient_id")
    )
    private Set<Patient> patients = new HashSet<>();

    // jeśli usuniemy doktora niekoniecznie chcemy wywalać jego wizyty
    @OneToMany(mappedBy = "doctor", fetch = FetchType.LAZY)
    private Set<Visit> visits = new HashSet<>();

}
