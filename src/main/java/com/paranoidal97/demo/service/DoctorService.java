package com.paranoidal97.demo.service;

import com.paranoidal97.demo.exception.DataAlreadyExistException;
import com.paranoidal97.demo.exception.DataNotFoundException;
import com.paranoidal97.demo.mapper.DoctorMapper;
import com.paranoidal97.demo.model.dto.doctor.DoctorDtoMain;
import com.paranoidal97.demo.model.entity.Doctor;
import com.paranoidal97.demo.repository.DoctorRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class DoctorService {
    private final DoctorRepository doctorRepository;
    private final DoctorMapper doctorMapper;
    public List<DoctorDtoMain> getAllDoctors() {
        log.info("Get all doctors");
        return doctorRepository.findAll().stream()
                .map(doctorMapper::toDtoMain)
                .collect(Collectors.toList());
    }

    public DoctorDtoMain getDoctor(Long id) {
        log.info("Get doctor with id '{}'", id);
        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("There is no such doctor"));
        return doctorMapper.toDtoMain(doctor);
    }

    public DoctorDtoMain addDoctor(DoctorDtoMain doctor) {
        log.info("create doctor '{}'", doctor.toString());
        Doctor entity = doctorMapper.toEntityFromMain(doctor);
        Optional<Doctor> doctorToFind = doctorRepository.findByEmail(entity.getEmail());
        if (doctorToFind.isPresent()) {
            throw new DataAlreadyExistException("Such doctor already exists");
        }
        return doctorMapper.toDtoMain(doctorRepository.save(entity));
    }

    public void deleteDoctor(Long id) {
        doctorRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("There is no such doctor"));
        doctorRepository.deleteById(id);
    }

    public DoctorDtoMain editDoctor(Long id, DoctorDtoMain doctorDto) {
        Doctor doctor = doctorMapper.toEntityFromMain(doctorDto);
        Doctor doctorToEdit = doctorRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("There is no such doctor"));
        doctorToEdit.setEmail(doctor.getEmail());
        doctorToEdit.setPassword(doctor.getPassword());
        doctorToEdit.setName(doctor.getName());
        doctorToEdit.setSurname(doctor.getSurname());
        return doctorMapper.toDtoMain(doctorToEdit);
    }
}
