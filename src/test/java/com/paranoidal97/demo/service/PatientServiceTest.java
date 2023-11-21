package com.paranoidal97.demo.service;

import com.paranoidal97.demo.data.TestDataFactory;
import com.paranoidal97.demo.exception.DataAlreadyExistException;
import com.paranoidal97.demo.exception.DataNotFoundException;
import com.paranoidal97.demo.mapper.PatientMapper;
import com.paranoidal97.demo.model.entity.Patient;
import com.paranoidal97.demo.repository.PatientRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;

// Ta adnotacja wskazuje, że testy będą korzystały z rozszerzenia Mockito
// do tworzenia i zarządzania atrapami (mockami) obiektów.
@ExtendWith(MockitoExtension.class)
public class PatientServiceTest {
    PatientMapper patientMapper;
    PatientRepository patientRepository;

    PatientService patientService;

    @BeforeEach
    void setup() {
        this.patientRepository = Mockito.mock(PatientRepository.class);
        this.patientMapper = Mappers.getMapper(PatientMapper.class);
        this.patientService = new PatientService(patientRepository, patientMapper);
    }

    @Test
    void getAllPatients_PatientsExists_PatientsReturned() {
        //given
        List<Patient> samplePatients = TestDataFactory.createSamplePatients();
        Mockito.when(patientRepository.findAll()).thenReturn(samplePatients);

        //when
        var result = patientService.getAllPatients();
        //then
        List<Patient> resultPatients = result.stream()
                .map(patientMapper::toEntity)
                .collect(Collectors.toList());
        Assertions.assertThat(resultPatients)
                .usingFieldByFieldElementComparator()
                .containsExactlyInAnyOrderElementsOf(samplePatients);
    }

    @Test
    void getPatient_PatientExist_PatientReturned() {
        //given
        Patient samplePatient = TestDataFactory.createSamplePatient();
        Mockito.when(patientRepository.findById(1L))
                .thenReturn(Optional.ofNullable(samplePatient));
        //when
        var result = patientService.getPatient(1L);
        //then
        assertEquals(samplePatient.getEmail(), result.getEmail());
        assertEquals(samplePatient.getPassword(), result.getPassword());
        assertEquals(samplePatient.getIdCardNo(), result.getIdCardNo());
        assertEquals(samplePatient.getFirstName(), result.getFirstName());
        assertEquals(samplePatient.getLastName(), result.getLastName());
        assertEquals(samplePatient.getPhoneNumber(), result.getPhoneNumber());
        assertEquals(samplePatient.getBirthday(), result.getBirthday());
    }


    @Test
    void getPatient_PatientNotExist_DataNotFoundException() {
        //given
        Mockito.when(patientRepository.findById(1L))
                .thenReturn(Optional.empty());
        //when
        Exception exception = assertThrows(DataNotFoundException.class, () -> {
            patientService.getPatient(1L);
        });
        //then
        assertEquals("There is no such user", exception.getMessage());

    }

    @Test
    void addPatient_PatientDoesNotExist_PatientReturned() {
        //given
        Patient samplePatient = TestDataFactory.createSamplePatient();
        Mockito.when(patientRepository.save(samplePatient)).thenReturn(samplePatient);
        //when
        var result = patientService.addPatient(patientMapper.toDto(samplePatient));
        //then
        assertEquals(samplePatient.getEmail(), result.getEmail());
        assertEquals(samplePatient.getPassword(), result.getPassword());
        assertEquals(samplePatient.getIdCardNo(), result.getIdCardNo());
        assertEquals(samplePatient.getFirstName(), result.getFirstName());
        assertEquals(samplePatient.getLastName(), result.getLastName());
        assertEquals(samplePatient.getPhoneNumber(), result.getPhoneNumber());
        assertEquals(samplePatient.getBirthday(), result.getBirthday());
    }


    @Test
    void addPatient_PatientExist_DataAlreadyExistException() {
        //given
        Patient samplePatient = TestDataFactory.createSamplePatient();
        Mockito.when(patientRepository.findByEmail(any()))
                .thenReturn(Optional.ofNullable(samplePatient));
        //when
        Exception exception = assertThrows(DataAlreadyExistException.class, () -> {
            patientService.addPatient(patientMapper.toDto(samplePatient));
        });
        //then
        assertEquals("Such user already exists", exception.getMessage());
    }

    @Test
    void deletePatient_PatientExist_PatientDeleted() {
        //given
        Patient patient = TestDataFactory.createSamplePatient();
        Mockito.when(patientRepository.findById(1L))
                .thenReturn(Optional.ofNullable(patient));
        //when
        patientService.deletePatient(1L);
        //then
        Mockito.verify(patientRepository, Mockito.times(1)).deleteById(1L);
    }


    @Test
    void deletePatient_PatientDoesNotExist_DataNotFoundException() {
        //given
        Mockito.when(patientRepository.findById(1L))
                .thenReturn(Optional.empty());
        //when
        Exception exception = assertThrows(DataNotFoundException.class, () -> {
            patientService.deletePatient(1L);
        });
        //then
        assertEquals("There is no such user", exception.getMessage());
    }

    @Test
    void editPatient_PatientExist_PatientReturned() {
        //given
        Patient patient = TestDataFactory.createSamplePatient();
        Patient patientEdited = Patient.builder()
                .email("jan.kowalski@example.com")
                .password("passwordEdit")
                .idCardNo("idEdit")
                .firstName("JanEditt")
                .lastName("KowalskiEdut")
                .phoneNumber("+48 123 456 799")
                .birthday(LocalDate.of(1989, 5, 15))
                .build();
        Mockito.when(patientRepository.findById(1L))
                .thenReturn(Optional.ofNullable(patient));
        //when
        var result = patientService.editPatient(1L, patientMapper.toDto(patientEdited));
        //then
        assertEquals("jan.kowalski@example.com", result.getEmail());
        assertEquals("passwordEdit", result.getPassword());
        assertEquals("idEdit", result.getIdCardNo());
        assertEquals("JanEditt", result.getFirstName());
        assertEquals("KowalskiEdut", result.getLastName());
        assertEquals("+48 123 456 799", result.getPhoneNumber());
        assertEquals(LocalDate.of(1989, 5, 15), result.getBirthday());
    }

    @Test
    void editPatient_PatientDoesNotExist_DataNotFoundException() {
        //given
        Mockito.when(patientRepository.findById(1L))
                .thenReturn(Optional.empty());
        //when
        Exception exception = assertThrows(DataNotFoundException.class, () -> {
            patientService.getPatient(1L);
        });
        //then
        assertEquals("There is no such user", exception.getMessage());
    }

    @Test
    void changePassword_PatientExist_Success() {
        //given
        Patient samplePatient = TestDataFactory.createSamplePatient();
        Mockito.when(patientRepository.findById(1L))
                .thenReturn(Optional.ofNullable(samplePatient));
        //when
        patientService.changePassword(1L, "newPassword");
        //then
        assertEquals(samplePatient.getPassword(), "newPassword");
    }

    @Test
    void changePassword_PatientDoesNotExist_DataNotFoundException() {
        //given
        Mockito.when(patientRepository.findById(1L))
                .thenReturn(Optional.empty());
        //when and then
        Exception exception = assertThrows(DataNotFoundException.class, () -> {
            patientService.getPatient(1L);
        });
        //then
        assertEquals("There is no such user", exception.getMessage());
    }

    @Test
    void changePassword_PatientExist_NewPasswordNull() {
        //given
        Patient samplePatient = TestDataFactory.createSamplePatient();
        Mockito.when(patientRepository.findById(1L))
                .thenReturn(Optional.ofNullable(samplePatient));
        //when
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            patientService.changePassword(1L, null);
        });
        //then
        assertEquals("The password cannot be null or empty", exception.getMessage());
    }

    @Test
    void changePassword_PatientExist_NewPasswordEmpty() {
        //given
        Patient samplePatient = TestDataFactory.createSamplePatient();
        Mockito.when(patientRepository.findById(1L))
                .thenReturn(Optional.ofNullable(samplePatient));
        //when
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            patientService.changePassword(1L, "");
        });
        //then
        assertEquals("The password cannot be null or empty", exception.getMessage());
    }

    @Test
    void changePassword_PatientExist_NewPasswordEqualsOld() {
        //given
        Patient samplePatient = TestDataFactory.createSamplePatient();
        Mockito.when(patientRepository.findById(1L))
                .thenReturn(Optional.ofNullable(samplePatient));
        //when
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            patientService.changePassword(1L, samplePatient.getPassword());
        });
        //then
        assertEquals("The new password cannot be the same as the old one", exception.getMessage());
    }
}
