package com.paranoidal97.demo.service;

import com.paranoidal97.demo.data.TestDataFactory;
import com.paranoidal97.demo.exception.DataAlreadyExistException;
import com.paranoidal97.demo.exception.DataNotFoundException;
import com.paranoidal97.demo.model.entity.Patient;
import com.paranoidal97.demo.repository.PatientRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

// Ta adnotacja wskazuje, że testy będą korzystały z rozszerzenia Mockito
// do tworzenia i zarządzania atrapami (mockami) obiektów.
@ExtendWith(MockitoExtension.class)
@Sql(scripts = {"file:src/test/java/com/paranoidal97/demo/data/patient/insert_data.sql"},
        config = @SqlConfig(encoding = "utf-8", transactionMode = SqlConfig.TransactionMode.ISOLATED),
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = {"file:src/test/java/com/paranoidal97/demo/data/patient/clear_data.sql"},
        config = @SqlConfig(encoding = "utf-8", transactionMode = SqlConfig.TransactionMode.ISOLATED),
        executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class PatientServiceTest {

    @Mock
    PatientRepository patientRepository;

    @InjectMocks
    PatientService patientService;

    @Test
    void getAllPatients_PatientsExists_PatientsReturned() {
        //given
        List<Patient> samplePatients = TestDataFactory.createSamplePatients();
        Mockito.when(patientRepository.findAll()).thenReturn(samplePatients);
        //when
        var result = patientService.getAllPatients();
        //then
        Assertions.assertThat(result)
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
        var result = patientService.addPatient(samplePatient);
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
        Mockito.when(patientRepository.findById(any()))
                .thenReturn(Optional.ofNullable(samplePatient));
        //when
        Exception exception = assertThrows(DataAlreadyExistException.class, () -> {
            patientService.addPatient(samplePatient);
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
        var result = patientService.editPatient(1L, patientEdited);
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
