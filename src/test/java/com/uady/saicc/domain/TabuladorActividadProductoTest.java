package com.uady.saicc.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.uady.saicc.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TabuladorActividadProductoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TabuladorActividadProducto.class);
        TabuladorActividadProducto tabuladorActividadProducto1 = new TabuladorActividadProducto();
        tabuladorActividadProducto1.setId(1L);
        TabuladorActividadProducto tabuladorActividadProducto2 = new TabuladorActividadProducto();
        tabuladorActividadProducto2.setId(tabuladorActividadProducto1.getId());
        assertThat(tabuladorActividadProducto1).isEqualTo(tabuladorActividadProducto2);
        tabuladorActividadProducto2.setId(2L);
        assertThat(tabuladorActividadProducto1).isNotEqualTo(tabuladorActividadProducto2);
        tabuladorActividadProducto1.setId(null);
        assertThat(tabuladorActividadProducto1).isNotEqualTo(tabuladorActividadProducto2);
    }
}
