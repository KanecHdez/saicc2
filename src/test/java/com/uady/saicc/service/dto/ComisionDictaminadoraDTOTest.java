package com.uady.saicc.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.uady.saicc.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ComisionDictaminadoraDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ComisionDictaminadoraDTO.class);
        ComisionDictaminadoraDTO comisionDictaminadoraDTO1 = new ComisionDictaminadoraDTO();
        comisionDictaminadoraDTO1.setId(1L);
        ComisionDictaminadoraDTO comisionDictaminadoraDTO2 = new ComisionDictaminadoraDTO();
        assertThat(comisionDictaminadoraDTO1).isNotEqualTo(comisionDictaminadoraDTO2);
        comisionDictaminadoraDTO2.setId(comisionDictaminadoraDTO1.getId());
        assertThat(comisionDictaminadoraDTO1).isEqualTo(comisionDictaminadoraDTO2);
        comisionDictaminadoraDTO2.setId(2L);
        assertThat(comisionDictaminadoraDTO1).isNotEqualTo(comisionDictaminadoraDTO2);
        comisionDictaminadoraDTO1.setId(null);
        assertThat(comisionDictaminadoraDTO1).isNotEqualTo(comisionDictaminadoraDTO2);
    }
}
