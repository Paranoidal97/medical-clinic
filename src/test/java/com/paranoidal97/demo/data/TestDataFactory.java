package com.paranoidal97.demo.data;

import com.paranoidal97.demo.model.entity.Doctor;
import com.paranoidal97.demo.model.entity.Patient;
import com.paranoidal97.demo.model.entity.Visit;
import com.paranoidal97.demo.model.enums.VisitType;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TestDataFactory {

    public static List<Patient> createSamplePatients() {
        List<Patient> patients = new ArrayList<>();

        Patient patient1 = Patient.builder()
                .email("jan.kowalski@example.com")
                .password("password123")
                .idCardNo("ABC123456")
                .firstName("Jan")
                .lastName("Kowalski")
                .phoneNumber("+48 123 456 789")
                .birthday(LocalDate.of(1980, 5, 15))
                .build();

        // Pacjent 2
        Patient patient2 = Patient.builder()
                .email("anna.nowak@example.com")
                .password("securepwd456")
                .idCardNo("XYZ789012")
                .firstName("Anna")
                .lastName("Nowak")
                .phoneNumber("+48 987 654 321")
                .birthday(LocalDate.of(1995, 8, 22))
                .build();

        // Pacjent 3
        Patient patient3 = Patient.builder()
                .email("marek.nowicki@example.com")
                .password("test123")
                .idCardNo("DEF456789")
                .firstName("Marek")
                .lastName("Nowicki")
                .phoneNumber("+48 111 222 333")
                .birthday(LocalDate.of(1975, 3, 10))
                .build();

        return Arrays.asList(patient1, patient2, patient3);
    }

    public static Patient createSamplePatient() {

        Patient patient1 = Patient.builder()
                .email("jan.kowalski@example.com")
                .password("password123")
                .idCardNo("ABC123456")
                .firstName("Jan")
                .lastName("Kowalski")
                .phoneNumber("+48 123 456 789")
                .birthday(LocalDate.of(1980, 5, 15))
                .build();

        return patient1;

    }

    public static List<Doctor> createSampleDoctors() {
        List<Doctor> doctors = new ArrayList<>();

        Doctor doctor1 = Doctor.builder()
                .name("Kacper")
                .surname("Kowalski")
                .email("doktor1@gmail.com")
                .build();

        Doctor doctor2 = Doctor.builder()
                .name("Anna")
                .surname("Nowak")
                .email("doctor2@example.com")
                .build();

        Doctor doctor3 = Doctor.builder()
                .name("Marek")
                .surname("Nowicki")
                .email("doctor3@example.com")
                .build();

        // Add more doctors as needed...

        doctors.add(doctor1);
        doctors.add(doctor2);
        doctors.add(doctor3);

        return doctors;
    }

    public static Doctor createSampleDoctor() {

        Doctor doctor1 = Doctor.builder()
                .name("Kacper")
                .surname("Kowalski")
                .email("doktor1@gmail.com")
                .build();

        return doctor1;
    }

    public static List<Visit> createSampleVisits() {
        List<Visit> visits = new ArrayList<>();

        // Tworzenie pojedynczej wizyty
        Visit visit1 = Visit.builder()
                .startTime(LocalDateTime.now())
                .endTime(LocalDateTime.now().plusHours(1))
                .visitType(VisitType.CREATED)
                .price(BigDecimal.valueOf(100))
                .build();

        // Tworzenie kilku wizyt
        Visit visit2 = Visit.builder()
                .startTime(LocalDateTime.now().plusDays(1))
                .endTime(LocalDateTime.now().plusDays(1).plusHours(1))
                .visitType(VisitType.CREATED)
                .price(BigDecimal.valueOf(120))
                .build();

        Visit visit3 = Visit.builder()
                .startTime(LocalDateTime.now().plusDays(2))
                .endTime(LocalDateTime.now().plusDays(2).plusHours(1))
                .visitType(VisitType.CREATED)
                .price(BigDecimal.valueOf(80))
                .build();

        // Dodawanie wizyt do listy
        visits.add(visit1);
        visits.add(visit2);
        visits.add(visit3);

        return visits;
    }

    public static Visit createSampleVisit() {
        // Tworzenie pojedynczej wizyty
        Visit visit = Visit.builder()
                .startTime(LocalDateTime.now())
                .endTime(LocalDateTime.now().plusHours(1))
                .visitType(VisitType.CREATED)
                .price(BigDecimal.valueOf(100))
                .build();

        return visit;
    }
}
