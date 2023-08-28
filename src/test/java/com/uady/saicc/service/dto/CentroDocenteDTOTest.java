package com.uady.saicc.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.uady.saicc.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CentroDocenteDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CentroDocenteDTO.class);
        CentroDocenteDTO centroDocenteDTO1 = new CentroDocenteDTO();
        centroDocenteDTO1.setId(1L);
        CentroDocenteDTO centroDocenteDTO2 = new CentroDocenteDTO();
        assertThat(centroDocenteDTO1).isNotEqualTo(centroDocenteDTO2);
        centroDocenteDTO2.setId(centroDocenteDTO1.getId());
        assertThat(centroDocenteDTO1).isEqualTo(centroDocenteDTO2);
        centroDocenteDTO2.setId(2L);
        assertThat(centroDocenteDTO1).isNotEqualTo(centroDocenteDTO2);
        centroDocenteDTO1.setId(null);
        assertThat(centroDocenteDTO1).isNotEqualTo(centroDocenteDTO2);
    }
}
