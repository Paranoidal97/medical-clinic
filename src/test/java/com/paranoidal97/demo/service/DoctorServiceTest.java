package com.paranoidal97.demo.service;

import com.paranoidal97.demo.data.TestDataFactory;
import com.paranoidal97.demo.exception.DataAlreadyExistException;
import com.paranoidal97.demo.exception.DataNotFoundException;
import com.paranoidal97.demo.model.entity.Doctor;
import com.paranoidal97.demo.model.entity.Patient;
import com.paranoidal97.demo.repository.DoctorRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class DoctorServiceTest {
    @Mock
    DoctorRepository doctorRepository;

    @InjectMocks
    DoctorService doctorService;

    @Test
    void getAllDoctors_doctorsExists_DoctorsReturned() {
        //given
        List<Doctor> sampleDoctors = TestDataFactory.createSampleDoctors();
        Mockito.when(doctorRepository.findAll()).thenReturn(sampleDoctors);
        //when
        var result = doctorService.getAllDoctors();
        //then
        Assertions.assertThat(result)
                .usingFieldByFieldElementComparator()
                .containsExactlyInAnyOrderElementsOf(sampleDoctors);

    }

    @Test
    void getDoctor_DoctorExist_DoctorReturned() {
        Doctor sampleDoctor = TestDataFactory.createSampleDoctor();
        Mockito.when(doctorRepository.findById(1L))
                .thenReturn(Optional.ofNullable(sampleDoctor));
        //when
        var result = doctorService.getDoctor(1L);
        //then
        assertEquals(sampleDoctor.getName(), result.getName());
        assertEquals(sampleDoctor.getSurname(), result.getSurname());
        assertEquals(sampleDoctor.getEmail(), result.getEmail());
    }

    @Test
    void getDoctor_DoctorExist_DataNotFoundException() {
        //given
        Mockito.when(doctorRepository.findById(1L))
                .thenReturn(Optional.empty());
        //when
        Exception exception = assertThrows(DataNotFoundException.class, () -> {
            doctorService.getDoctor(1L);
        });
        //then
        assertEquals("There is no such doctor", exception.getMessage());
    }

    @Test
    void addDoctor_DoctorDoesNotExist_DoctorReturned() {
        //given
        Doctor sampleDoctor = TestDataFactory.createSampleDoctor();
        Mockito.when(doctorRepository.save(sampleDoctor)).thenReturn(sampleDoctor);
        //when
        var result = doctorService.addDoctor(sampleDoctor);
        //then
        assertEquals(sampleDoctor.getName(), result.getName());
        assertEquals(sampleDoctor.getSurname(), result.getSurname());
        assertEquals(sampleDoctor.getEmail(), result.getEmail());
    }

    @Test
    void addDoctor_DoctorExist_DataAlreadyExistException() {
        //given
        Doctor sampleDoctor = TestDataFactory.createSampleDoctor();
        Mockito.when(doctorRepository.findByEmail(any()))
                .thenReturn(Optional.ofNullable(sampleDoctor));
        //when
        Exception exception = assertThrows(DataAlreadyExistException.class, () -> {
            doctorService.addDoctor(sampleDoctor);
        });
        //then
        assertEquals("Such doctor already exists", exception.getMessage());
    }

    @Test
    void deleteDoctor_DoctorExist_DoctorDeleted() {
        //given
        Doctor sampleDoctor = TestDataFactory.createSampleDoctor();
        Mockito.when(doctorRepository.findById(1L))
                .thenReturn(Optional.ofNullable(sampleDoctor));
        //when
        doctorService.deleteDoctor(1L);
        //then
        Mockito.verify(doctorRepository, Mockito.times(1)).deleteById(1L);
    }

    @Test
    void deleteDoctor_DoctorDoesNotExist_DataNotFoundException() {
        //given
        Doctor sampleDoctor = TestDataFactory.createSampleDoctor();
        Mockito.when(doctorRepository.findById(1L))
                .thenReturn(Optional.empty());
        //when
        Exception exception = assertThrows(DataNotFoundException.class, () -> {
            doctorService.deleteDoctor(1L);
        });
        //then
        assertEquals("There is no such doctor", exception.getMessage());
    }
//
//    //TODO dodaj edit Doctor
//

}
