package com.paranoidal97.demo.data;

import com.paranoidal97.demo.model.Patient;

import java.time.LocalDate;
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
}
