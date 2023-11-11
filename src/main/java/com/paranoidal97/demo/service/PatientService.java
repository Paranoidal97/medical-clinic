package com.paranoidal97.demo.service;

import com.paranoidal97.demo.exception.DataAlreadyExistException;
import com.paranoidal97.demo.exception.DataNotFoundException;
import com.paranoidal97.demo.mapper.PatientMapper;
import com.paranoidal97.demo.model.dto.patient.PatientDtoMain;
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
    public List<PatientDtoMain> getAllPatients() {
        log.trace("A TRACE Message");
        log.debug("A DEBUG Message");
        log.info("An INFO Message");
        log.warn("A WARN Message");
        log.error("An ERROR Message");
        return patientRepository.findAll().stream().map(patientMapper::toDtoMain).collect(Collectors.toList());
    }

    public PatientDtoMain getPatient(Long id) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("There is no such user"));
        return patientMapper.toDtoMain(patient);
    }

    public PatientDtoMain addPatient(PatientDtoMain patient) {
        Patient entityFromMain = patientMapper.toEntityFromMain(patient);
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

    public PatientDtoMain editPatient(Long id, PatientDtoMain dto) {
        Patient patient = patientMapper.toEntityFromMain(dto);
        Patient patientToEdit = patientRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("There is no such user"));
        patientToEdit.setPassword(patient.getPassword());
        patientToEdit.setFirstName(patient.getFirstName());
        patientToEdit.setLastName(patient.getLastName());
        patientToEdit.setPhoneNumber(patient.getPhoneNumber());
        patientToEdit.setEmail(patient.getEmail());
        patientToEdit.setIdCardNo(patient.getIdCardNo());
        patientToEdit.setBirthday(patient.getBirthday());
        return patientMapper.toDtoMain(patientToEdit);
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
