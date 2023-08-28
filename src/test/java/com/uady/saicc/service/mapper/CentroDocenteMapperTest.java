package com.uady.saicc.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CentroDocenteMapperTest {

    private CentroDocenteMapper centroDocenteMapper;

    @BeforeEach
    public void setUp() {
        centroDocenteMapper = new CentroDocenteMapperImpl();
    }
}
