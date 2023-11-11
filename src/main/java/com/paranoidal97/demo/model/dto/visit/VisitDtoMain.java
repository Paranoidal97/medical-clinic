package com.paranoidal97.demo.model.dto.visit;

import com.paranoidal97.demo.model.entity.Doctor;
import com.paranoidal97.demo.model.entity.Patient;
import com.paranoidal97.demo.model.enums.VisitType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDateTime;
@Getter
@Builder
@AllArgsConstructor
public class VisitDtoMain {
    private Long id;
    private Patient patient;
    private Doctor doctor;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private VisitType visitType;
    private BigDecimal price;
}
