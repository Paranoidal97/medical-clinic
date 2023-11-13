package com.paranoidal97.demo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.paranoidal97.demo.model.enums.VisitType;
import com.paranoidal97.demo.repository.VisitRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Sql(scripts = {"file:src/test/resources/visit/insert_data.sql"},
        config = @SqlConfig(encoding = "utf-8", transactionMode = SqlConfig.TransactionMode.ISOLATED),
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = {"file:src/test/resources/visit/clear_data.sql"},
        config = @SqlConfig(encoding = "utf-8", transactionMode = SqlConfig.TransactionMode.ISOLATED),
        executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class VisitControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    VisitRepository visitRepository;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    @Rollback
    void getAllVisitsTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/visits"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].price").value(100))
                .andExpect(jsonPath("$[0].visitType").value(VisitType.CREATED.toString()));
    }

    //TODO zamien rollback i ditiesContext na skrypty ssql
    @Test
    void getVisitTest() throws Exception {
        ;
        mockMvc.perform(MockMvcRequestBuilders.get("/visits/{id}", Long.valueOf(1)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.price").value(100))
                .andExpect(jsonPath("$.visitType").value(VisitType.CREATED.toString()));
    }


}
