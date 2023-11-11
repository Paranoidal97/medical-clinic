package com.paranoidal97.demo.model.entity;

import com.paranoidal97.demo.model.enums.VisitType;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table
public class Visit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    // musi to być zeby wywalić wizyte z setu u pacjenta
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "patient_id")
    private Patient patient;
    // musi to być żeby wywalic wizyte z setu u doktora
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "doctor_id")
    private Doctor doctor;
    //  data i czas będą w formacie ISO 8601, co ma format "yyyy-MM-dd'T'HH:mm:ss".
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime startTime;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime endTime;
    @Enumerated(
            EnumType.STRING
    )
    private VisitType visitType;
    private BigDecimal price;
}
