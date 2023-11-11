package com.paranoidal97.demo.controller;

import com.paranoidal97.demo.model.dto.visit.VisitDto;
import com.paranoidal97.demo.model.dto.visit.VisitDtoMain;
import com.paranoidal97.demo.model.entity.Visit;
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
    public List<VisitDtoMain> getAllVisits(){
        return visitService.getAllVisits();
    }

    @GetMapping("/{id}")
    public VisitDtoMain getVisit(@PathVariable Long id){
        return visitService.getVisit(id);
    }

    @PostMapping
    public VisitDtoMain addVisit(@RequestBody VisitDtoMain visit){
        return visitService.addVisit(visit);
    }

    @PatchMapping("/{visitId}/assignPatient")
    public VisitDtoMain assignPatient(@RequestBody Long patientId, @PathVariable Long visitId){
        return visitService.assignPatient(patientId,visitId);
    }

    @PatchMapping("/{visitId}")
    public VisitDtoMain editVisit(@PathVariable Long visitId, @RequestBody VisitDtoMain visit){
        return visitService.editVisit(visitId, visit);
    }

}
