package com.paranoidal97.demo.controller;

import com.paranoidal97.demo.model.Patient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
// klasy jako kontrolera RESTful. Kontrolery RESTful są odpowiedzialne za obsługę żądań
// HTTP i zwracanie odpowiedzi w formacie JSON lub innym formacie
@RequestMapping("/patients")
// Jest to adnotacja używana do mapowania żądań HTTP na metody kontrolera. W tym przypadku,
// wszystkie żądania HTTP, które rozpoczynają się od /patients, zostaną przekierowane do tej kontrolera
public class PatientController {
    private List<Patient> patients;

    @GetMapping
    public List<Patient> getAllPatients() {
        return patients;
    }

    @GetMapping("/{email}")
    public Patient getPatients(@PathVariable String email) {
        return patients.stream()
                .filter(patient -> patient.getEmail().equals(email))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Nie ma takiego użytkownika w bazie"));
    }

    @PostMapping
    public void addPatient(@RequestBody Patient patient){
        patients.add(patient);
    }

    @DeleteMapping("/{email}")
    public void deletePatient(@PathVariable String email){
        Patient patientToDelete = getPatients(email);
        patients.remove(patientToDelete);
    }

    @PutMapping("/{email}")
    public void editPatient(@PathVariable String email @RequestBody Patient patientData){
        Patient patientToEdit = getPatients(email);

    }
}
