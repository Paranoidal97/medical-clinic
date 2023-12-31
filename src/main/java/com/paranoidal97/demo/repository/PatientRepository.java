package com.paranoidal97.demo.repository;

import com.paranoidal97.demo.model.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface PatientRepository extends JpaRepository<Patient, Long> {

    Optional<Patient> findByEmail(String email);

    Optional<Patient> deleteByEmail(String email);
}
