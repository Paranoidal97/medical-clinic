package com.paranoidal97.demo.repository;

import com.paranoidal97.demo.model.Patient;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class PatientRepositoryImpl implements PatientRepository {
    private final List<Patient> patients = new ArrayList<>();

    @Override
    public List<Patient> getAllPatients() {
        return patients;
    }

    @Override
    public Patient addPatient(Patient patient) {
        patients.add(patient);
        return patient;
    }

    @Override
    public Optional<Patient> getPatient(String email) {
        return patients.stream()
                .filter(patient -> patient.getEmail().equals(email))
                .findFirst();
    }

    @Override
    public Optional<Patient> deleteByEmail(String email) {
        Optional<Patient> patientToDelete = getPatient(email);
        if (patientToDelete.isEmpty()) {
            Optional.empty();
        }
        patients.remove(patientToDelete.get());
        return patientToDelete;
    }
}
