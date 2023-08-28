package com.uady.saicc.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.uady.saicc.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CentroDocenteTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CentroDocente.class);
        CentroDocente centroDocente1 = new CentroDocente();
        centroDocente1.setId(1L);
        CentroDocente centroDocente2 = new CentroDocente();
        centroDocente2.setId(centroDocente1.getId());
        assertThat(centroDocente1).isEqualTo(centroDocente2);
        centroDocente2.setId(2L);
        assertThat(centroDocente1).isNotEqualTo(centroDocente2);
        centroDocente1.setId(null);
        assertThat(centroDocente1).isNotEqualTo(centroDocente2);
    }
}
