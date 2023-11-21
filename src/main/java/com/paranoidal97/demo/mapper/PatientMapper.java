package com.paranoidal97.demo.mapper;

import com.paranoidal97.demo.model.dto.patient.PatientDto;
import com.paranoidal97.demo.model.entity.Patient;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, unmappedSourcePolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface PatientMapper {
    PatientDto toDto(Patient patient);

    Patient toEntity(PatientDto patientDTO);

}
