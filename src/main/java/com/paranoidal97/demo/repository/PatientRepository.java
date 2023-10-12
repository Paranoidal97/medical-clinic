package com.paranoidal97.demo.repository;

import com.paranoidal97.demo.model.Patient;

import java.util.List;
import java.util.Optional;

public interface PatientRepository {
    List<Patient> getAllPatients();

    Optional<Patient> getPatient(String email);

    Patient addPatient(Patient patient);

    Optional<Patient> deleteByEmail(String email);
}
