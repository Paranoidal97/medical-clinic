package com.paranoidal97.demo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.paranoidal97.demo.data.TestDataFactory;
import com.paranoidal97.demo.model.entity.Doctor;
import com.paranoidal97.demo.repository.DoctorRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Sql(scripts = {"file:src/test/resources/doctor/insert_data.sql"},
        config = @SqlConfig(encoding = "utf-8", transactionMode = SqlConfig.TransactionMode.ISOLATED),
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = {"file:src/test/resources/doctor/clear_data.sql"},
        config = @SqlConfig(encoding = "utf-8", transactionMode = SqlConfig.TransactionMode.ISOLATED),
        executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class DoctorControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    DoctorRepository doctorRepository;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void getAllDoctorsTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/doctors"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].email").value("john.doe@example.com"));
        System.out.println("TestA");
    }

    @Test
    void getDoctorTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/doctors/{id}", 1L))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("john.doe@example.com"));
    }

    @Test
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
    void deleteDoctorTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/doctors/{id}", 1L))
                .andDo(print())
                .andExpect(status().isNoContent());
    }


}
