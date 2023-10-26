package com.paranoidal97.demo.service;

import com.paranoidal97.demo.exception.DataNotFoundException;
import com.paranoidal97.demo.exception.InvalidAppointmentTimeException;
import com.paranoidal97.demo.exception.PastAppointmentException;
import com.paranoidal97.demo.model.entity.Patient;
import com.paranoidal97.demo.model.entity.Visit;
import com.paranoidal97.demo.model.entity.VisitType;
import com.paranoidal97.demo.repository.PatientRepository;
import com.paranoidal97.demo.repository.VisitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class VisitService {
    private final VisitRepository visitRepository;
    private final PatientRepository patientRepository;

    public List<Visit> getAllVisits() {
        return visitRepository.findAll();
    }

    public Visit addVisit(Visit visit) {
        if (visit.getStartTime().isBefore(LocalDateTime.now())) {
            throw new PastAppointmentException("You can't create visit in past, if you think you are Emmett Brown please report to your nerest psychiatric hospital");
        }
        if (visit.getStartTime().getMinute() % 15 == 0 && visit.getEndTime().getMinute() % 15 == 0) {
            throw new InvalidAppointmentTimeException("Godzina 1.02 chce mi się spać"); //TODO
        }
        if (visit.getDoctor().getVisits().stream().anyMatch(visit2 ->
                visit != visit2 &&
                        visit.getStartTime().isBefore(visit2.getEndTime()) && visit.getEndTime().isAfter(visit2.getStartTime())
        )) {
            throw new InvalidAppointmentTimeException("That doctor already have visit in this time");
        }
        visitRepository.save(visit);
        return visit;
    }

    public Visit getVisit(Long id) {
        return visitRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("There is no such visit"));
    }

    public Visit assignPatient(Long patientId, Long visitId) {
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
        return visitToAssign;
    }

    public Visit editVisit(Long visitId, Visit visit) {
        Visit visitToAssign = visitRepository.findById(visitId)
                .orElseThrow(
                        () -> new DataNotFoundException("There is no such Visit")
                );
        visitToAssign.setVisitType(visit.getVisitType());
        //TODO
        // ale załatwia nam to sprawe statusów
        // ale jednak burdel i przydałyby sie walidatory
        //  ide spać (Y) 2.29
        return visitToAssign;
    }

    @Scheduled(cron = "0 0 0 * * ?")
    public void setVisitTypeToOutdated() {
        LocalDateTime currentDateTime = LocalDateTime.now();

        for (Visit visit : visitRepository.findAllOutdated(currentDateTime)) {
            visit.setVisitType(VisitType.OUTDATED);
        }


    }
}
