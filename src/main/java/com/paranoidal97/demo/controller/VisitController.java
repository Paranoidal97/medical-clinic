package com.paranoidal97.demo.controller;

import com.paranoidal97.demo.model.dto.visit.VisitDto;
import com.paranoidal97.demo.service.VisitService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/visits")
@RequiredArgsConstructor
public class VisitController {
    private final VisitService visitService;

    @GetMapping
    public List<VisitDto> getAllVisits() {
        return visitService.getAllVisits();
    }

    @GetMapping("/{id}")
    public VisitDto getVisit(@PathVariable Long id) {
        return visitService.getVisit(id);
    }

    @PostMapping
    public VisitDto addVisit(@RequestBody VisitDto visit) {
        return visitService.addVisit(visit);
    }

    @PatchMapping("/{visitId}/assignPatient")
    public VisitDto assignPatient(@RequestBody Long patientId, @PathVariable Long visitId) {
        return visitService.assignPatient(patientId, visitId);
    }

    @PatchMapping("/{visitId}")
    public VisitDto editVisit(@PathVariable Long visitId, @RequestBody VisitDto visit) {
        return visitService.editVisit(visitId, visit);
    }

}
