package com.paranoidal97.demo.controller;

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
    public List<Visit> getAllVisits(){
        return visitService.getAllVisits();
    }

    @GetMapping("/{id}")
    public Visit getVisit(@PathVariable Long id){
        return visitService.getVisit(id);
    }

    @PostMapping
    public Visit addVisit(@RequestBody Visit visit){
        return visitService.addVisit(visit);
    }

    @PatchMapping("/{visitId}/assignPatient")
    public Visit assignPatient(@RequestBody Long patientId, @PathVariable Long visitId){
        return visitService.assignPatient(patientId,visitId);
    }

}
