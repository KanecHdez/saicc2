package com.uady.saicc.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AcademicoMapperTest {

    private AcademicoMapper academicoMapper;

    @BeforeEach
    public void setUp() {
        academicoMapper = new AcademicoMapperImpl();
    }
}
