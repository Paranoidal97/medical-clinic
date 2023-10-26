package com.paranoidal97.demo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.paranoidal97.demo.data.TestDataFactory;
import com.paranoidal97.demo.model.entity.Patient;
import com.paranoidal97.demo.repository.PatientRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.util.Optional;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class PatientControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    PatientRepository patientRepository;

    @Autowired
    ObjectMapper objectMapper;

    @BeforeEach
    void setup() {
        Optional<Patient> patient = patientRepository.findByEmail("jan.kowalski@example.com");

        if (patient.isPresent()) {
            patientRepository.deleteByEmail("jan.kowalski@example.com");
        }

        patientRepository.save(TestDataFactory.createSamplePatient());
    }

    @Test
    @Rollback
    void getAllPatientsTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/patients"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].email").value("jan.kowalski@example.com"));
    }

    @Test
    @Rollback
    void getPatientTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/patients/{email}", "jan.kowalski@example.com"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("jan.kowalski@example.com"));
    }

    @Test
    @Rollback
    void createPatientTest() throws Exception {
        Patient samplePatient = TestDataFactory.createSamplePatient();
        samplePatient.setEmail("test@test.pl");
        samplePatient.setIdCardNo("BCD123");

        mockMvc.perform(MockMvcRequestBuilders.post("/patients")
                        .content(objectMapper.writeValueAsString(samplePatient))
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                )
                .andDo(print())
                .andExpect(jsonPath("$.email").value("test@test.pl"));

    }

    @Test
    @Rollback
    void deletePatientTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/patients/{email}", "jan.kowalski@example.com"))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    @Rollback
    void editPatientTest() throws Exception {
        Patient patientToEdite = Patient.builder()
                .email("jan.kowalski@example.com")
                .password("password123")
                .idCardNo("ABC123456")
                .firstName("Jan Po Edycji")
                .lastName("Kowalski")
                .phoneNumber("+48 123 456 789")
                .birthday(LocalDate.of(1980, 5, 15))
                .build();

        mockMvc.perform(MockMvcRequestBuilders.put("/patients/{email}", "jan.kowalski@example.com")
                        .content(objectMapper.writeValueAsString(patientToEdite))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @Rollback
    void changePasswordTest() throws Exception {
        String newPassword = "newPassword123";

        mockMvc.perform(MockMvcRequestBuilders.patch("/patients/{email}", "jan.kowalski@example.com")
                        .content(newPassword)
                )
                .andExpect(status().isNoContent());
    }


}
