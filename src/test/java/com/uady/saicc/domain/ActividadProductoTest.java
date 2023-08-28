package com.uady.saicc.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.uady.saicc.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ActividadProductoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ActividadProducto.class);
        ActividadProducto actividadProducto1 = new ActividadProducto();
        actividadProducto1.setId(1L);
        ActividadProducto actividadProducto2 = new ActividadProducto();
        actividadProducto2.setId(actividadProducto1.getId());
        assertThat(actividadProducto1).isEqualTo(actividadProducto2);
        actividadProducto2.setId(2L);
        assertThat(actividadProducto1).isNotEqualTo(actividadProducto2);
        actividadProducto1.setId(null);
        assertThat(actividadProducto1).isNotEqualTo(actividadProducto2);
    }
}
