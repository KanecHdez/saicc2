package com.uady.saicc.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.uady.saicc.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ActividadProductoDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ActividadProductoDTO.class);
        ActividadProductoDTO actividadProductoDTO1 = new ActividadProductoDTO();
        actividadProductoDTO1.setId(1L);
        ActividadProductoDTO actividadProductoDTO2 = new ActividadProductoDTO();
        assertThat(actividadProductoDTO1).isNotEqualTo(actividadProductoDTO2);
        actividadProductoDTO2.setId(actividadProductoDTO1.getId());
        assertThat(actividadProductoDTO1).isEqualTo(actividadProductoDTO2);
        actividadProductoDTO2.setId(2L);
        assertThat(actividadProductoDTO1).isNotEqualTo(actividadProductoDTO2);
        actividadProductoDTO1.setId(null);
        assertThat(actividadProductoDTO1).isNotEqualTo(actividadProductoDTO2);
    }
}
