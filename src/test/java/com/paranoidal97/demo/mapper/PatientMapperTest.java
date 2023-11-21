package com.paranoidal97.demo.mapper;

import com.paranoidal97.demo.data.TestDataFactory;
import com.paranoidal97.demo.model.entity.Patient;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mapstruct.factory.Mappers;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class PatientMapperTest {
    PatientMapper patientMapper = Mappers.getMapper(PatientMapper.class);

    @ParameterizedTest
    @MethodSource("patientsData")
    void PatientMapperTest(Patient patient) {

        var result = patientMapper.toDto(patient);

        assertAll(
                () -> assertEquals(patient.getId(), result.getId()),
                () -> assertEquals(patient.getFirstName(), result.getFirstName()),
                () -> assertEquals(patient.getLastName(), result.getLastName()),
                () -> assertEquals(patient.getEmail(), result.getEmail())
        );
    }

    public static Stream<Arguments> patientsData() {
        Patient samplePatient1 = TestDataFactory.createSamplePatient();
        Patient samplePatient2 = TestDataFactory.createSamplePatient();


        return Stream.of(
                Arguments.of(samplePatient1),
                Arguments.of(samplePatient2)
        );
    }
}
