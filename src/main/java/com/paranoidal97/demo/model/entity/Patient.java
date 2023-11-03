package com.paranoidal97.demo.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
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

    @ManyToMany(
            mappedBy = "patients"
    )
    private Set<Doctor> doctors = new HashSet<>();

}
