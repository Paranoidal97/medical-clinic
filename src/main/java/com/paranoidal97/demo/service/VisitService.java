package com.paranoidal97.demo.service;

import com.paranoidal97.demo.exception.DataNotFoundException;
import com.paranoidal97.demo.exception.InvalidAppointmentTimeException;
import com.paranoidal97.demo.exception.PastAppointmentException;
import com.paranoidal97.demo.mapper.VisitMapper;
import com.paranoidal97.demo.model.dto.visit.VisitDto;
import com.paranoidal97.demo.model.dto.visit.VisitDtoMain;
import com.paranoidal97.demo.model.entity.Patient;
import com.paranoidal97.demo.model.entity.Visit;
import com.paranoidal97.demo.model.enums.VisitType;
import com.paranoidal97.demo.repository.PatientRepository;
import com.paranoidal97.demo.repository.VisitRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class VisitService {
    private final VisitRepository visitRepository;
    private final PatientRepository patientRepository;
    private final VisitMapper visitMapper;

    public List<VisitDtoMain> getAllVisits() {
        return visitRepository.findAll().stream().map(visitMapper::toDtoMain).collect(Collectors.toList());
    }

    public VisitDtoMain getVisit(Long id) {
        Visit visit = visitRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("There is no such visit"));
        return visitMapper.toDtoMain(visit);
    }

    public VisitDtoMain addVisit(VisitDtoMain visitDto) {
        Visit visit = visitMapper.toEntityFromMain(visitDto);
        if (visit.getStartTime().isBefore(LocalDateTime.now())) {
            throw new PastAppointmentException("You can't create visit in past, if you think you are Emmett Brown please report to your nerest psychiatric hospital");
        }
        if (visit.getStartTime().getMinute() % 15 == 0 && visit.getEndTime().getMinute() % 15 == 0) {
            throw new InvalidAppointmentTimeException("Godzina 1.02 chce mi się spać"); //TODO
        }
        if (visit.getDoctor().getVisits() != null) {
            if (visit.getDoctor().getVisits().stream().anyMatch(visit2 ->
                    visit != visit2 &&
                            visit.getStartTime().isBefore(visit2.getEndTime()) && visit.getEndTime().isAfter(visit2.getStartTime())
            )) {
                throw new InvalidAppointmentTimeException("That doctor already have visit in this time");
            }
        }
        visitRepository.save(visit);
        return visitMapper.toDtoMain(visit);
    }

    public VisitDtoMain assignPatient(Long patientId, Long visitId) {
        Visit visitToAssign = visitRepository.findById(visitId)
                .orElseThrow(
                        () -> new DataNotFoundException("There is no such Visit")
                );
        Patient patientToAssign = patientRepository.findById(patientId)
                .orElseThrow(
                        () -> new DataNotFoundException("There is no such User")
                );
        visitToAssign.setVisitType(VisitType.SCHEDULED);
        visitToAssign.setPatient(patientToAssign);
        return visitMapper.toDtoMain(visitToAssign);
    }

    public VisitDtoMain editVisit(Long visitId, VisitDtoMain visitDto) {
        Visit visit = visitMapper.toEntityFromMain(visitDto);
        Visit visitToAssign = visitRepository.findById(visitId)
                .orElseThrow(
                        () -> new DataNotFoundException("There is no such Visit")
                );
        if (visit.getStartTime().isBefore(LocalDateTime.now())) {
            throw new PastAppointmentException("You can't create visit in past, if you think you are Emmett Brown please report to your nerest psychiatric hospital");
        }
        if (visit.getStartTime().getMinute() % 15 == 0 && visit.getEndTime().getMinute() % 15 == 0) {
            throw new InvalidAppointmentTimeException("Godzina 1.02 chce mi się spać"); //TODO
        }
        if (visitToAssign.getVisitType().isTransitionAllowed(visit.getVisitType(), true)) {
            visitToAssign.setVisitType(visit.getVisitType());
        }
        return visitMapper.toDtoMain(visitToAssign);
    }

    @Scheduled(cron = "0 0 0 * * ?")
    public void setVisitTypeToOutdated() {
        LocalDateTime currentDateTime = LocalDateTime.now();

        for (Visit visit : visitRepository.findAllOutdated(currentDateTime)) {
            visit.setVisitType(VisitType.OUTDATED);
        }
    }
}
