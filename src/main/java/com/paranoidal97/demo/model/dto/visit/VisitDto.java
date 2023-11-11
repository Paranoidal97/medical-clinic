package com.paranoidal97.demo.model.dto.visit;

import com.paranoidal97.demo.model.enums.VisitType;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@RequiredArgsConstructor
public class VisitDto {
    private Long id;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private VisitType visitType;
    private BigDecimal price;

}
