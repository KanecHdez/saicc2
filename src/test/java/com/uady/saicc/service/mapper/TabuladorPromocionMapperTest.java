package com.uady.saicc.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TabuladorPromocionMapperTest {

    private TabuladorPromocionMapper tabuladorPromocionMapper;

    @BeforeEach
    public void setUp() {
        tabuladorPromocionMapper = new TabuladorPromocionMapperImpl();
    }
}
