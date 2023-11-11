package com.paranoidal97.demo.service;

import com.paranoidal97.demo.data.TestDataFactory;
import com.paranoidal97.demo.exception.DataNotFoundException;
import com.paranoidal97.demo.exception.IllegalApointmentTransition;
import com.paranoidal97.demo.exception.PastAppointmentException;
import com.paranoidal97.demo.model.entity.Doctor;
import com.paranoidal97.demo.model.entity.Patient;
import com.paranoidal97.demo.model.entity.Visit;
import com.paranoidal97.demo.model.enums.VisitType;
import com.paranoidal97.demo.repository.PatientRepository;
import com.paranoidal97.demo.repository.VisitRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class VisitServiceTest {
    @Mock
    VisitRepository visitRepository;
    @Mock
    PatientRepository patientRepository;
    @InjectMocks
    VisitService visitService;

    @Test
    void getAllVisits_visitsExists_VisitsReturned() {
        //given
        List<Visit> sampleVisits = TestDataFactory.createSampleVisits();
        Mockito.when(visitRepository.findAll()).thenReturn(sampleVisits);
        //when
        var result = visitService.getAllVisits();
        //then
        Assertions.assertThat(result)
                .usingFieldByFieldElementComparator()
                .containsExactlyInAnyOrderElementsOf(sampleVisits);
    }

    @Test
    void getVisit_visitExists_VisitReturned() {
        //given
        Visit sampleVisit = TestDataFactory.createSampleVisit();
        Mockito.when(visitRepository.findById(any()))
                .thenReturn(Optional.ofNullable(sampleVisit));
        //when
        var result = visitService.getVisit(any());
        //then

    }

    @Test
    void addVisit_visitNotExists_VisitReturned() {
        //given
        Visit sampleVisit = TestDataFactory.createSampleVisit();
        Doctor sampleDoctor = TestDataFactory.createSampleDoctor();
        sampleVisit.setStartTime(LocalDateTime.now());
        sampleVisit.setDoctor(sampleDoctor);
        //when
        var result = visitService.addVisit(sampleVisit);
        //then
        assertEquals(sampleVisit.getVisitType(), result.getVisitType());
    }

    @Test
    void addVisit_visitNotExists_VisitHaveNoDoctor() {
        //given
        Visit sampleVisit = TestDataFactory.createSampleVisit();
        Doctor sampleDoctor = TestDataFactory.createSampleDoctor();
        sampleVisit.setStartTime(LocalDateTime.now());
        //when
        var result = visitService.addVisit(sampleVisit);
        //then
        assertEquals(sampleVisit.getVisitType(), result.getVisitType());
    }

    @Test
    void addVisit_visitNotExists_PastAppointmentException() {
        //given
        Visit sampleVisit = TestDataFactory.createSampleVisit();
        //when
        Exception exception = assertThrows(PastAppointmentException.class, () -> {
            visitService.addVisit(sampleVisit);
        });
        //then
        assertEquals("You can't create visit in past, if you think you are Emmett Brown please report to your nerest psychiatric hospital", exception.getMessage());
    }

    @Test
    void assignPatient_PatientExists_VisitReturned() {
        //given
        Visit sampleVisit = TestDataFactory.createSampleVisit();
        Patient samplePatient = TestDataFactory.createSamplePatient();
        Mockito.when(visitRepository.findById(any())).thenReturn(Optional.ofNullable(sampleVisit));
        Mockito.when(patientRepository.findById(any())).thenReturn(Optional.ofNullable(samplePatient));
        //when
        var result = visitService.assignPatient(1L, 1L);
        //then
        assertEquals(sampleVisit.getVisitType(), result.getVisitType());
        assertEquals(result.getPatient(), samplePatient);
    }

    @Test
    void editVisit_visitExists_VisitReturned() {

    }

    @ParameterizedTest
    @MethodSource("dataToIllegalTransition")
    void editVisit_IllegalTransition_IllegalTransitionException(VisitType from, VisitType to, boolean isPatientNotNull) {
        //given
        Visit sampleVisit1 = TestDataFactory.createSampleVisit();
        sampleVisit1.setVisitType(from);

        //when and then
        Exception exception = assertThrows(IllegalApointmentTransition.class, () -> {
            from.isTransitionAllowed(VisitType.valueOf(to.toString()), isPatientNotNull);
        });
        //then
        assertEquals("This visit transition from state is illegal or patient is missing.", exception.getMessage());
    }

    @ParameterizedTest
    @MethodSource("dataToPositiveTransition")
    void editVisit_PositiveTransition_TransitionAllowed(VisitType type1, VisitType type2, boolean isPatientNotNull) {
        //given
        Visit sampleVisit1 = TestDataFactory.createSampleVisit();
        sampleVisit1.setVisitType(type1);

        //when and then
        boolean isAllowed = type1.isTransitionAllowed(VisitType.valueOf(type2.toString()), isPatientNotNull);
        //then
        assertTrue(isAllowed);
    }

    public static Stream<Arguments> dataToIllegalTransition() {
        return Stream.of(
                // Negatywne przejścia z brakiem pacjenta
                Arguments.of(VisitType.CREATED, VisitType.SCHEDULED, false),
                Arguments.of(VisitType.CREATED, VisitType.CANCELLED, false),
                Arguments.of(VisitType.CREATED, VisitType.COMPLETED, false),
                Arguments.of(VisitType.CREATED, VisitType.CANCELLED, true),
                Arguments.of(VisitType.CREATED, VisitType.COMPLETED, true),
                Arguments.of(VisitType.SCHEDULED, VisitType.CANCELLED, false),
                Arguments.of(VisitType.SCHEDULED, VisitType.COMPLETED, false)
        );
    }

    public static Stream<Arguments> dataToPositiveTransition() {
        return Stream.of(
                // Pozytywne przejścia
                Arguments.of(VisitType.CREATED, VisitType.SCHEDULED, true),
                Arguments.of(VisitType.CREATED, VisitType.OUTDATED, true),
                Arguments.of(VisitType.CREATED, VisitType.OUTDATED, false),
                Arguments.of(VisitType.SCHEDULED, VisitType.CANCELLED, true),
                Arguments.of(VisitType.SCHEDULED, VisitType.COMPLETED, true)
        );
    }
}
