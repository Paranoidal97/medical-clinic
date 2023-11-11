package com.paranoidal97.demo.controller;

import com.paranoidal97.demo.model.dto.patient.PatientDtoMain;
import com.paranoidal97.demo.model.entity.Patient;
import com.paranoidal97.demo.service.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
// klasy jako kontrolera RESTful. Kontrolery RESTful są odpowiedzialne za obsługę żądań
// HTTP i zwracanie odpowiedzi w formacie JSON lub innym formacie
@RequestMapping("/patients")
// Jest to adnotacja używana do mapowania żądań HTTP na metody kontrolera. W tym przypadku,
// wszystkie żądania HTTP, które rozpoczynają się od /patients, zostaną przekierowane do tej kontrolera
@RequiredArgsConstructor
public class PatientController {
    private final PatientService service;

    @GetMapping
    public List<PatientDtoMain> getAllPatients() {
        return service.getAllPatients();
    }

    @GetMapping("/{id}")
    public PatientDtoMain getPatient(@PathVariable Long id) {
        return service.getPatient(id);
    }

    @PostMapping
    public PatientDtoMain addPatient(@RequestBody PatientDtoMain patient) {
        service.addPatient(patient);
        return patient;
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePatient(@PathVariable Long id) {
        service.deletePatient(id);
    }

    @PutMapping("/{id}")
    public PatientDtoMain editPatient(@PathVariable Long id, @RequestBody PatientDtoMain patient) {
        return service.editPatient(id, patient);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PatchMapping("/{id}")
    public void changePassword(@PathVariable Long id, @RequestBody String password) {
        service.changePassword(id, password);
    }

}
