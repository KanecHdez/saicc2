package com.uady.saicc.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.uady.saicc.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AcademicoDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AcademicoDTO.class);
        AcademicoDTO academicoDTO1 = new AcademicoDTO();
        academicoDTO1.setId(1L);
        AcademicoDTO academicoDTO2 = new AcademicoDTO();
        assertThat(academicoDTO1).isNotEqualTo(academicoDTO2);
        academicoDTO2.setId(academicoDTO1.getId());
        assertThat(academicoDTO1).isEqualTo(academicoDTO2);
        academicoDTO2.setId(2L);
        assertThat(academicoDTO1).isNotEqualTo(academicoDTO2);
        academicoDTO1.setId(null);
        assertThat(academicoDTO1).isNotEqualTo(academicoDTO2);
    }
}
