package com.paranoidal97.demo.mapper;


import com.paranoidal97.demo.data.TestDataFactory;
import com.paranoidal97.demo.model.dto.DoctorDto;
import com.paranoidal97.demo.model.entity.Doctor;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

public class DoctorMapperTest {
    DoctorMapper doctorMapper = Mappers.getMapper(DoctorMapper.class);

    @Test
    public void entityToDtoMapper(){
        Doctor sampleDoctor = TestDataFactory.createSampleDoctor();

    }

    @Test
    public void dtoToEntityMapper(){
        Doctor sampleDoctor = TestDataFactory.createSampleDoctor();

    }
}
