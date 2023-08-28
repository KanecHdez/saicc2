package com.uady.saicc.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TabuladorActividadProductoMapperTest {

    private TabuladorActividadProductoMapper tabuladorActividadProductoMapper;

    @BeforeEach
    public void setUp() {
        tabuladorActividadProductoMapper = new TabuladorActividadProductoMapperImpl();
    }
}
