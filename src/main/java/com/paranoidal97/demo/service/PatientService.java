package com.paranoidal97.demo.service;

import com.paranoidal97.demo.exception.DataAlreadyExsistException;
import com.paranoidal97.demo.exception.DataNotFoundException;
import com.paranoidal97.demo.model.Patient;
import com.paranoidal97.demo.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PatientService {
    private final PatientRepository patientRepository;

    public List<Patient> getAllPatients() {
        return patientRepository.getAllPatients();
    }

    public Patient getPatient(String email) {
        return patientRepository.getPatient(email)
                .orElseThrow(() -> new DataNotFoundException("Nie ma takiego użytkownika"));
    }

    public Patient addPatient(Patient patient) {
        Optional<Patient> patientToFind = patientRepository.getPatient(patient.getEmail());
        if(patientToFind.isPresent()){
            throw new DataAlreadyExsistException("Taki uzutkownik już istnieje");
        }
        patientRepository.addPatient(patient);
        return patient;

    }

    public void deletePatient(String email) {
        Optional<Patient> patientToFind = patientRepository.getPatient(email);
        if(patientToFind.isEmpty()){
            throw new DataNotFoundException("Nie ma takiego użytkownika");
        }
        patientRepository.deletebyEmail(email);
    }

    public Patient editPatient(String email, Patient patient) {
        Patient patientToEdit = patientRepository.getPatient(email)
                .orElseThrow(() -> new DataNotFoundException("Nie ma takiego użytkownika"));
        patientToEdit.setPassword(patient.getPassword());
        patientToEdit.setFirstName(patient.getFirstName());
        patientToEdit.setLastName(patient.getLastName());
        patientToEdit.setPhoneNumber(patient.getPhoneNumber());
        patientToEdit.setEmail(patient.getEmail());
        return patientToEdit;
    }

    public void changePassword(String email, String password) {
        Patient patient = patientRepository.getPatient(email)
                .orElseThrow(() -> new DataNotFoundException("Nie ma takiego użytkownika"));
        if (password == null || password.isEmpty()) {
            throw new IllegalArgumentException("Hasło nie może być nullem lub być puste");
        } else if (password.equals(patient.getPassword())) {
            throw new IllegalArgumentException("Hasła są takie same");
        }
        patient.setPassword(password);

    }

}
