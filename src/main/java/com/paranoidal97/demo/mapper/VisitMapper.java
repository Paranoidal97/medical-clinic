package com.paranoidal97.demo.mapper;
import com.paranoidal97.demo.model.dto.PatientDto;
import com.paranoidal97.demo.model.dto.VisitDto;
import com.paranoidal97.demo.model.entity.Doctor;
import com.paranoidal97.demo.model.entity.Patient;
import com.paranoidal97.demo.model.entity.Visit;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.stream.Stream;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, unmappedSourcePolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface VisitMapper {
    VisitDto toDto(Visit visit);

    Visit toEntity(VisitDto visitDto);
}
