package com.uady.saicc.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.uady.saicc.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TabuladorActividadProductoDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TabuladorActividadProductoDTO.class);
        TabuladorActividadProductoDTO tabuladorActividadProductoDTO1 = new TabuladorActividadProductoDTO();
        tabuladorActividadProductoDTO1.setId(1L);
        TabuladorActividadProductoDTO tabuladorActividadProductoDTO2 = new TabuladorActividadProductoDTO();
        assertThat(tabuladorActividadProductoDTO1).isNotEqualTo(tabuladorActividadProductoDTO2);
        tabuladorActividadProductoDTO2.setId(tabuladorActividadProductoDTO1.getId());
        assertThat(tabuladorActividadProductoDTO1).isEqualTo(tabuladorActividadProductoDTO2);
        tabuladorActividadProductoDTO2.setId(2L);
        assertThat(tabuladorActividadProductoDTO1).isNotEqualTo(tabuladorActividadProductoDTO2);
        tabuladorActividadProductoDTO1.setId(null);
        assertThat(tabuladorActividadProductoDTO1).isNotEqualTo(tabuladorActividadProductoDTO2);
    }
}
