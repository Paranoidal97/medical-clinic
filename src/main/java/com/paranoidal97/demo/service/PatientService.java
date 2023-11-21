package com.paranoidal97.demo.service;

import com.paranoidal97.demo.exception.DataAlreadyExistException;
import com.paranoidal97.demo.exception.DataNotFoundException;
import com.paranoidal97.demo.mapper.PatientMapper;
import com.paranoidal97.demo.model.dto.patient.PatientDto;
import com.paranoidal97.demo.model.entity.Patient;
import com.paranoidal97.demo.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class PatientService {
    private final PatientRepository patientRepository;
    private final PatientMapper patientMapper;

    public List<PatientDto> getAllPatients() {
        return patientRepository.findAll()
                .stream()
                .map(patientMapper::toDto)
                .collect(Collectors.toList());
    }

    public PatientDto getPatient(Long id) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("There is no such user"));
        return patientMapper.toDto(patient);
    }

    public PatientDto addPatient(PatientDto patient) {
        Patient entityFromMain = patientMapper.toEntity(patient);
        Optional<Patient> patientToFind = patientRepository.findByEmail(entityFromMain.getEmail());
        if (patientToFind.isPresent()) {
            throw new DataAlreadyExistException("Such user already exists");
        }
        patientRepository.save(entityFromMain);
        return patient;

    }

    public void deletePatient(Long id) {
        patientRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("There is no such user"));
        patientRepository.deleteById(id);
    }

    public PatientDto editPatient(Long id, PatientDto dto) {
        Patient patient = patientMapper.toEntity(dto);
        Patient patientToEdit = patientRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("There is no such user"));
        patientToEdit.setPassword(patient.getPassword());
        patientToEdit.setFirstName(patient.getFirstName());
        patientToEdit.setLastName(patient.getLastName());
        patientToEdit.setPhoneNumber(patient.getPhoneNumber());
        patientToEdit.setEmail(patient.getEmail());
        patientToEdit.setIdCardNo(patient.getIdCardNo());
        patientToEdit.setBirthday(patient.getBirthday());
        return patientMapper.toDto(patientToEdit);
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
