package com.paranoidal97.demo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.paranoidal97.demo.data.TestDataFactory;
import com.paranoidal97.demo.model.entity.Visit;
import com.paranoidal97.demo.model.entity.VisitType;
import com.paranoidal97.demo.repository.VisitRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
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
public class VisitControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    VisitRepository visitRepository;

    @Autowired
    ObjectMapper objectMapper;

    @BeforeEach
    void setup(){
        Optional<Visit>  visit = visitRepository.findById(Long.valueOf(1));

        if(visit.isPresent()){
            visitRepository.deleteById(Long.valueOf(1));
        }

        visitRepository.save(TestDataFactory.createSampleVisit());

    }

    @Test
    @Rollback
    void getAllVisitsTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/visits"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].price").value(100))
                .andExpect(jsonPath("$[0].visitType").value(VisitType.CREATED));
    }

    @Test
    @Rollback
    void getVisitTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/visits/{id}", Long.valueOf(1)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.price").value(100))
                .andExpect(jsonPath("$.visitType").value(VisitType.CREATED));
    }


}
