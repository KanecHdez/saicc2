package com.uady.saicc.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PuestoMapperTest {

    private PuestoMapper puestoMapper;

    @BeforeEach
    public void setUp() {
        puestoMapper = new PuestoMapperImpl();
    }
}
