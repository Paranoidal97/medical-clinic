package com.paranoidal97.demo.service;

import com.paranoidal97.demo.repository.DoctorRepository;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class DoctorServiceTest {
    @Mock
    DoctorRepository doctorRepository;

    @InjectMocks
    DoctorService doctorService;
}
