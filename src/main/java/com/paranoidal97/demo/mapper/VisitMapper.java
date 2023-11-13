package com.paranoidal97.demo.mapper;

import com.paranoidal97.demo.model.dto.visit.VisitDto;
import com.paranoidal97.demo.model.entity.Visit;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, unmappedSourcePolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface VisitMapper {
    VisitDto toDto(Visit visit);

    Visit toEntity(VisitDto visitDto);
}
