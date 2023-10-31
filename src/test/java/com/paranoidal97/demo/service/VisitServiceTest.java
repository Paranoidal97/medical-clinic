package com.paranoidal97.demo.service;

import com.paranoidal97.demo.data.TestDataFactory;
import com.paranoidal97.demo.exception.IllegalApointmentTransition;
import com.paranoidal97.demo.model.entity.Visit;
import com.paranoidal97.demo.model.enums.VisitType;
import com.paranoidal97.demo.repository.VisitRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class VisitServiceTest {
    @Mock
    VisitRepository visitRepository;

    @InjectMocks
    VisitService visitService;

    @Test
    void getAllVisits_visitsExists_VisitsReturned(){

    }

    @Test
    void getVisit_visitExists_VisitReturned(){

    }

    @Test
    void addVisit_visitNotExists_VisitReturned(){

    }

    @Test
    void assignPatient_PatientExists_VisitReturned(){

    }

    @Test
    void editVisit_visitExists_VisitReturned(){

    }

    @ParameterizedTest
    @MethodSource("dataToIllegalTransition")
    void editVisit_IllegalTransition_IllegalTransitionException(VisitType type1, VisitType type2){
        //given
        Visit sampleVisit1 = TestDataFactory.createSampleVisit();
        sampleVisit1.setVisitType(type1);

        Mockito.when(visitRepository.findById(Long.valueOf("1")))
                .thenReturn(Optional.of(sampleVisit1));
        //when and then
        Exception exception = assertThrows(IllegalApointmentTransition.class, () -> {
            visitService.editVisit(Long.valueOf("1"), sampleVisit1);
        });
        //then
        assertEquals("This visit transition from state is illegal or patient is missing.", exception.getMessage());
    }

    public static Stream<Arguments> dataToIllegalTransition() {
        return Stream.of(
                // Przejścia z Created
                Arguments.of(VisitType.CREATED, VisitType.SCHEDULED, true),
                Arguments.of(VisitType.CREATED, VisitType.CANCELLED, true),
                Arguments.of(VisitType.CREATED, VisitType.COMPLETED, true),
                Arguments.of(VisitType.CREATED, VisitType.OUTDATED, true),
                Arguments.of(VisitType.CREATED, VisitType.CANCELLED, false),
                Arguments.of(VisitType.CREATED, VisitType.COMPLETED, false),
                Arguments.of(VisitType.CREATED, VisitType.OUTDATED, false),

                // Przejścia z Scheduled
                Arguments.of(VisitType.SCHEDULED, VisitType.CREATED, true),
                Arguments.of(VisitType.SCHEDULED, VisitType.COMPLETED, true),
                Arguments.of(VisitType.SCHEDULED, VisitType.OUTDATED, true),
                Arguments.of(VisitType.SCHEDULED, VisitType.CREATED, false),
                Arguments.of(VisitType.SCHEDULED, VisitType.COMPLETED, false),
                Arguments.of(VisitType.SCHEDULED, VisitType.OUTDATED, false),

                // Przejścia z Cancelled
                Arguments.of(VisitType.CANCELLED, VisitType.SCHEDULED, true),
                Arguments.of(VisitType.CANCELLED, VisitType.CREATED, true),
                Arguments.of(VisitType.CANCELLED, VisitType.COMPLETED, true),
                Arguments.of(VisitType.CANCELLED, VisitType.OUTDATED, true),
                Arguments.of(VisitType.CANCELLED, VisitType.SCHEDULED, false),
                Arguments.of(VisitType.CANCELLED, VisitType.CREATED, false),
                Arguments.of(VisitType.CANCELLED, VisitType.COMPLETED, false),
                Arguments.of(VisitType.CANCELLED, VisitType.OUTDATED, false),

                // Przejścia z Completed
                Arguments.of(VisitType.COMPLETED, VisitType.SCHEDULED, true),
                Arguments.of(VisitType.COMPLETED, VisitType.CREATED, true),
                Arguments.of(VisitType.COMPLETED, VisitType.CANCELLED, true),
                Arguments.of(VisitType.COMPLETED, VisitType.OUTDATED, true),
                Arguments.of(VisitType.COMPLETED, VisitType.SCHEDULED, false),
                Arguments.of(VisitType.COMPLETED, VisitType.CREATED, false),
                Arguments.of(VisitType.COMPLETED, VisitType.CANCELLED, false),
                Arguments.of(VisitType.COMPLETED, VisitType.OUTDATED, false),

                // Przejścia z Outdated
                Arguments.of(VisitType.OUTDATED, VisitType.CREATED, true),
                Arguments.of(VisitType.OUTDATED, VisitType.SCHEDULED, true),
                Arguments.of(VisitType.OUTDATED, VisitType.CANCELLED, true),
                Arguments.of(VisitType.OUTDATED, VisitType.COMPLETED, true),
                Arguments.of(VisitType.OUTDATED, VisitType.CREATED, false),
                Arguments.of(VisitType.OUTDATED, VisitType.SCHEDULED, false),
                Arguments.of(VisitType.OUTDATED, VisitType.CANCELLED, false),
                Arguments.of(VisitType.OUTDATED, VisitType.COMPLETED, false)
        );
    }
//    @Test
//    void getAllPatients_PatientsExists_PatientsReturned() {
//        //given
//        List<Patient> samplePatients = TestDataFactory.createSamplePatients();
//        Mockito.when(patientRepository.findAll()).thenReturn(samplePatients);
//        //when
//        var result = patientService.getAllPatients();
//        //then
//        Assertions.assertThat(result)
//                .usingFieldByFieldElementComparator()
//                .containsExactlyInAnyOrderElementsOf(samplePatients);
//    }
}
