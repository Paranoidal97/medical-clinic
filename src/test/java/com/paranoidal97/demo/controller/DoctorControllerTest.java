package com.paranoidal97.demo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.paranoidal97.demo.data.TestDataFactory;
import com.paranoidal97.demo.model.entity.Doctor;
import com.paranoidal97.demo.model.entity.Patient;
import com.paranoidal97.demo.repository.DoctorRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Optional;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class DoctorControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    DoctorRepository doctorRepository;

    @Autowired
    ObjectMapper objectMapper;

    @BeforeEach
    void setup() {
        Optional<Doctor> doctor = doctorRepository.findById(Long.valueOf(1));

        if(doctor.isPresent()){
            doctorRepository.deleteById(Long.valueOf(1));
        }
        doctorRepository.save(TestDataFactory.createSampleDoctor());
    }

    @Test
    @Rollback
    @DirtiesContext
    void getAllDoctorsTest() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.get("/doctors"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].email").value("doktor1@gmail.com"));
    }

    @Test
    @Rollback
    @DirtiesContext
    void getDoctorTest() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.get("/doctors/{id}", Long.valueOf(1)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("doktor1@gmail.com"));
    }

    @Test
    @Rollback
    @DirtiesContext
    void createDoctorTest() throws Exception {
        Doctor sampleDoctor = TestDataFactory.createSampleDoctor();
        sampleDoctor.setEmail("test@test.pl");

        mockMvc.perform(MockMvcRequestBuilders.post("/doctors")
                        .content(objectMapper.writeValueAsString(sampleDoctor))
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                )
                .andDo(print())
                .andExpect(jsonPath("$.email").value("test@test.pl"));
    }

    @Test
    @Rollback
    @DirtiesContext
    void deleteDoctorTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/doctors/{id}", Long.valueOf(1)))
                .andDo(print())
                .andExpect(status().isNoContent());
    }




}
