package com.paranoidal97.demo.service;

import com.paranoidal97.demo.repository.VisitRepository;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class VisitServiceTest {
    @Mock
    VisitRepository visitRepository;

    @InjectMocks
    VisitService visitService;

}
