package com.paranoidal97.demo.service;

import com.paranoidal97.demo.exception.DataAlreadyExistException;
import com.paranoidal97.demo.exception.DataNotFoundException;
import com.paranoidal97.demo.model.entity.Doctor;
import com.paranoidal97.demo.repository.DoctorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DoctorService {
    private final DoctorRepository doctorRepository;

    public List<Doctor> getAllDoctors() {
        return doctorRepository.findAll();
    }

    public Doctor getDoctor(Long id) {
        return doctorRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("There is no such doctor"));
    }

    public void deleteDoctor(Long id) {
        Optional<Doctor> doctor = doctorRepository.findById(id);
        if(doctor.isEmpty()){
            throw new DataNotFoundException("There is no such doctor");
        }
        doctorRepository.deleteById(id);
    }

    public Doctor addDoctor(Doctor doctor) {
        Optional<Doctor> doctorToFind = doctorRepository.findByEmail(doctor.getEmail());
        if(doctorToFind.isPresent()){
            throw new DataAlreadyExistException("Such doctor already exists");
        }
        doctorRepository.save(doctor);
        return doctor;
    }

    public Doctor editDoctor(Long id, Doctor doctor) {
        Doctor doctorToEdit = doctorRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("There is no such doctor"));
        doctorToEdit.setEmail(doctor.getEmail());
        doctorToEdit.setPassword(doctor.getPassword());
        doctorToEdit.setName(doctor.getName());
        doctorToEdit.setSurname(doctor.getSurname());
        return doctorToEdit;
    }
}
