package com.paranoidal97.demo.service;

import com.paranoidal97.demo.data.TestDataFactory;
import com.paranoidal97.demo.model.Patient;
import com.paranoidal97.demo.repository.PatientRepositoryImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;


// Ta adnotacja wskazuje, że testy będą korzystały z rozszerzenia Mockito
// do tworzenia i zarządzania atrapami (mockami) obiektów.
@ExtendWith(MockitoExtension.class)

public class PatientServiceTest {

    @Mock
    PatientRepositoryImpl patientRepository;

    @InjectMocks
    PatientService patientService;

    @Test
    void getAllPatients_PatientsExists_PatientsReturned(){
        //given
        List<Patient> samplePatients = TestDataFactory.createSamplePatients();

        Mockito.when(patientRepository.getAllPatients()).thenReturn(samplePatients);

        //when
        var result = patientService.getAllPatients();
        //then

        Assertions.assertEquals(samplePatients, result);
    }

    @Test
    void getPatient_PatientExist_PatientReturned(){
        //given
        Patient samplePatient = TestDataFactory.createSamplePatient();

        Mockito.when(patientRepository.getPatient("jan.kowalski@example.com")).thenReturn(Optional.ofNullable(samplePatient));

        //when
        var result = patientService.getPatient("jan.kowalski@example.com");
        //then

        Assertions.assertEquals(samplePatient, result);
    }

    @Test
    void addPatient_PatientDoesNotExist_PatientReturned(){
        //given
        Patient samplePatient = TestDataFactory.createSamplePatient();

        Mockito.when(patientRepository.addPatient(samplePatient)).thenReturn(samplePatient);
        //when
        var result = patientService.addPatient(samplePatient);
        //then

        Assertions.assertEquals(result,samplePatient);
    }

//    @Test
//    void_deletePatient_PatientExist_PatientDeleted(){
//        //given
//        String email = " jan.kowalski@example.com";
//        Mockito.when(patientRepository.deleteByEmail(email)).thenReturn()
//    }
}
