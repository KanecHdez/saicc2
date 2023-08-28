package com.uady.saicc.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.uady.saicc.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PeriodoDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PeriodoDTO.class);
        PeriodoDTO periodoDTO1 = new PeriodoDTO();
        periodoDTO1.setId(1L);
        PeriodoDTO periodoDTO2 = new PeriodoDTO();
        assertThat(periodoDTO1).isNotEqualTo(periodoDTO2);
        periodoDTO2.setId(periodoDTO1.getId());
        assertThat(periodoDTO1).isEqualTo(periodoDTO2);
        periodoDTO2.setId(2L);
        assertThat(periodoDTO1).isNotEqualTo(periodoDTO2);
        periodoDTO1.setId(null);
        assertThat(periodoDTO1).isNotEqualTo(periodoDTO2);
    }
}
