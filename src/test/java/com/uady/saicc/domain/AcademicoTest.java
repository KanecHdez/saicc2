package com.uady.saicc.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.uady.saicc.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AcademicoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Academico.class);
        Academico academico1 = new Academico();
        academico1.setId(1L);
        Academico academico2 = new Academico();
        academico2.setId(academico1.getId());
        assertThat(academico1).isEqualTo(academico2);
        academico2.setId(2L);
        assertThat(academico1).isNotEqualTo(academico2);
        academico1.setId(null);
        assertThat(academico1).isNotEqualTo(academico2);
    }
}
