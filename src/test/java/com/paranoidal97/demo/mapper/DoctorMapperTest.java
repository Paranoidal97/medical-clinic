package com.paranoidal97.demo.mapper;


import com.paranoidal97.demo.data.TestDataFactory;
import com.paranoidal97.demo.model.entity.Doctor;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mapstruct.factory.Mappers;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class DoctorMapperTest {
    DoctorMapper doctorMapper = Mappers.getMapper(DoctorMapper.class);

//    @ParameterizedTest
//    @MethodSource("doctorsData")
//    void DoctorMapperTest(Doctor doctor) {
//        var result = doctorMapper(doctor);
//
//        assertAll(
//                () -> assertEquals(doctor.getId(), result.getId()),
//                () -> assertEquals(doctor.getName(), result.getName()),
//                () -> assertEquals(doctor.getSurname(), result.getSurname()),
//                () -> assertEquals(doctor.getEmail(), result.getEmail())
//        );
//    }
//
//    public static Stream<Arguments> doctorsData() {
//        Doctor sampleDoctor1 = TestDataFactory.createSampleDoctor();
//        Doctor sampleDoctor2 = TestDataFactory.createSampleDoctor();
//
//
//        return Stream.of(
//                Arguments.of(sampleDoctor1),
//                Arguments.of(sampleDoctor2)
//        );
//    }
}
