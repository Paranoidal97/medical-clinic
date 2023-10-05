package com.paranoidal97.demo.controller;

import com.paranoidal97.demo.model.Patient;
import com.paranoidal97.demo.service.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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
    public List<Patient> getAllPatients() {
        return service.getAllPatients();
    }

    @GetMapping("/{email}")
    public Patient getPatients(@PathVariable String email) {
        return service.getPatients(email);
    }

    @PostMapping
    public void addPatient(@RequestBody Patient patient){
        service.addPatient(patient);
    }

    @DeleteMapping("/{email}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePatient(@PathVariable String email){
        service.deletePatient(email);
    }

    @PutMapping("/{email}")
    public void editPatient(@PathVariable String email, @RequestBody Patient patient){
        service.editPatient(email,patient);
    }

    @PatchMapping("/{email}")
    public void changePassword(@PathVariable String email, @RequestBody String password ){
        service.changePassword(email, password);
    }

}
