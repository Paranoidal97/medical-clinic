package com.paranoidal97.demo.mapper;

import com.paranoidal97.demo.data.TestDataFactory;
import com.paranoidal97.demo.model.entity.Visit;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mapstruct.factory.Mappers;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class VisitMapperTest {
    VisitMapper visitMapper = Mappers.getMapper(VisitMapper.class);

    @ParameterizedTest
    @MethodSource("visitsData")
    void VisitMapperTest(Visit visit) {
        var result = visitMapper.toDto(visit);

        assertAll(
                () -> assertEquals(visit.getId(), result.getId())
        );
    }

    public static Stream<Arguments> visitsData() {
        Visit sampleVisit1 = TestDataFactory.createSampleVisit();
        Visit sampleVisit2 = TestDataFactory.createSampleVisit();


        return Stream.of(
                Arguments.of(sampleVisit1),
                Arguments.of(sampleVisit2)
        );
    }
}
