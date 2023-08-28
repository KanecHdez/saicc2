package com.uady.saicc.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.uady.saicc.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PuestoDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PuestoDTO.class);
        PuestoDTO puestoDTO1 = new PuestoDTO();
        puestoDTO1.setId(1L);
        PuestoDTO puestoDTO2 = new PuestoDTO();
        assertThat(puestoDTO1).isNotEqualTo(puestoDTO2);
        puestoDTO2.setId(puestoDTO1.getId());
        assertThat(puestoDTO1).isEqualTo(puestoDTO2);
        puestoDTO2.setId(2L);
        assertThat(puestoDTO1).isNotEqualTo(puestoDTO2);
        puestoDTO1.setId(null);
        assertThat(puestoDTO1).isNotEqualTo(puestoDTO2);
    }
}
