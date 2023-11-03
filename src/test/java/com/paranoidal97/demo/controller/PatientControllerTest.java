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
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.util.Optional;

@SpringBootTest
@AutoConfigureMockMvc
@Sql(scripts = {"file:src/test/resources/patient/insert_data.sql"},
        config = @SqlConfig(encoding = "utf-8", transactionMode = SqlConfig.TransactionMode.ISOLATED),
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = {"file:src/test/resources/patient/clear_data.sql"},
        config = @SqlConfig(encoding = "utf-8", transactionMode = SqlConfig.TransactionMode.ISOLATED),
        executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class PatientControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    PatientRepository patientRepository;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void getAllPatientsTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/patients"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].email").value("patient1@example.com"));
    }

    @Test
    void getPatientTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/patients/{id}", 1L))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("patient1@example.com"));
    }

    @Test
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
    void deletePatientTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/patients/{id}", 1L))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
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

        mockMvc.perform(MockMvcRequestBuilders.put("/patients/{id}", 1L)
                        .content(objectMapper.writeValueAsString(patientToEdite))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void changePasswordTest() throws Exception {
        String newPassword = "newPassword123";

        mockMvc.perform(MockMvcRequestBuilders.patch("/patients/{id}", 1L)
                        .content(newPassword)
                )
                .andExpect(status().isNoContent());
    }


}
