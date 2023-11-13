package com.paranoidal97.demo.mapper;

import com.paranoidal97.demo.model.dto.doctor.DoctorDto;
import com.paranoidal97.demo.model.entity.Doctor;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, unmappedSourcePolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface DoctorMapper {
    DoctorDto toDto(Doctor doctor);

    Doctor toEntity(DoctorDto doctor);

}