package com.paranoidal97.demo.controller;

import com.paranoidal97.demo.model.dto.doctor.DoctorDto;
import com.paranoidal97.demo.service.DoctorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/doctors")
@RequiredArgsConstructor
public class DoctorController {
    private final DoctorService service;

    @GetMapping
    public List<DoctorDto> getAllDoctors() {
        return service.getAllDoctors();
    }

    @GetMapping("/{id}")
    public DoctorDto getDoctor(@PathVariable Long id) {
        return service.getDoctor(id);
    }

    @PostMapping
    public DoctorDto addDoctor(@RequestBody DoctorDto doctor) {
        service.addDoctor(doctor);
        return doctor;
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteDoctor(@PathVariable Long id) {
        service.deleteDoctor(id);
    }

    @PutMapping("/{id}")
    public DoctorDto editDoctor(@PathVariable Long id, @RequestBody DoctorDto doctor) {
        return service.editDoctor(id, doctor);
    }
}
