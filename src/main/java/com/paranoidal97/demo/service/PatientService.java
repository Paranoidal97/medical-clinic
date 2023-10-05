package com.paranoidal97.demo.service;

import com.paranoidal97.demo.model.Patient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PatientService {
    private final List<Patient> patients;

    private Patient getPatient(String email) {
        return patients.stream()
                .filter(patient -> patient.getEmail().equals(email))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Nie ma takiego użytkownika w bazie"));
    }

    public List<Patient> getAllPatients() {
        return new ArrayList<>(patients);
    }

    public Patient getPatients(String email) {
        return patients.stream()
                .filter(patient -> patient.getEmail().equals(email))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Nie ma takiego użytkownika w bazie"));
    }

    public void addPatient(Patient patient) {
        if (patient.getEmail() == null || patient.getEmail().isEmpty()) {
            throw new IllegalArgumentException("Email pacjenta jest wymagany.");
        } else {
            patients.add(patient);
        }
    }

    public void deletePatient(String email) {
        Patient patientToDelete = getPatient(email);
        patients.remove(patientToDelete);
    }

    public void editPatient(String email, Patient patient) {
        Patient patientToEdit = getPatients(email);
        patientToEdit.setPassword(patient.getPassword());
        patientToEdit.setFirstName(patient.getFirstName());
        patientToEdit.setLastName(patient.getLastName());
        patientToEdit.setPhoneNumber(patient.getPhoneNumber());
        patientToEdit.setEmail(patient.getEmail());
    }

    public void changePassword(String email, String password) {
        Patient patient = getPatient(email);
        if (password == null || password.isEmpty()) {
            throw new IllegalArgumentException("Hasło nie może być nullem lub być puste");
        } else if (password.equals(patient.getPassword())) {
            throw new IllegalArgumentException("Hasła są takie same");
        } else {
            patient.setPassword(password);
        }
    }

}
