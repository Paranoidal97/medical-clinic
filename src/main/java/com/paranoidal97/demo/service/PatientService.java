package com.paranoidal97.demo.service;

import com.paranoidal97.demo.exception.DataAlreadyExistException;
import com.paranoidal97.demo.exception.DataNotFoundException;
import com.paranoidal97.demo.model.entity.Patient;
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
        return patientRepository.findAll();
    }

    public Patient getPatient(Long id) {
        return patientRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("There is no such user"));
    }

    public Patient addPatient(Patient patient) {
        Optional<Patient> patientToFind = patientRepository.findById(patient.getId());
        if (patientToFind.isPresent()) {
            throw new DataAlreadyExistException("Such user already exists");
        }
        patientRepository.save(patient);
        return patient;

    }

    public void deletePatient(Long id) {
        Optional<Patient> patientToFind = patientRepository.findById(id);
        if (patientToFind.isEmpty()) {
            throw new DataNotFoundException("There is no such user");
        }
        patientRepository.deleteById(id);
    }

    public Patient editPatient(Long id, Patient patient) {
        Patient patientToEdit = patientRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("There is no such user"));
        patientToEdit.setPassword(patient.getPassword());
        patientToEdit.setFirstName(patient.getFirstName());
        patientToEdit.setLastName(patient.getLastName());
        patientToEdit.setPhoneNumber(patient.getPhoneNumber());
        patientToEdit.setEmail(patient.getEmail());
        patientToEdit.setIdCardNo(patient.getIdCardNo());
        patientToEdit.setBirthday(patient.getBirthday());
        return patientToEdit;
    }

    public void changePassword(Long id, String password) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("There is no such user"));
        if (password == null || password.isEmpty()) {
            throw new IllegalArgumentException("The password cannot be null or empty");
        } else if (password.equals(patient.getPassword())) {
            throw new IllegalArgumentException("The new password cannot be the same as the old one");
        }
        patient.setPassword(password);

    }

}
