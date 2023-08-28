package com.uady.saicc.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.uady.saicc.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ComisionDictaminadoraTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ComisionDictaminadora.class);
        ComisionDictaminadora comisionDictaminadora1 = new ComisionDictaminadora();
        comisionDictaminadora1.setId(1L);
        ComisionDictaminadora comisionDictaminadora2 = new ComisionDictaminadora();
        comisionDictaminadora2.setId(comisionDictaminadora1.getId());
        assertThat(comisionDictaminadora1).isEqualTo(comisionDictaminadora2);
        comisionDictaminadora2.setId(2L);
        assertThat(comisionDictaminadora1).isNotEqualTo(comisionDictaminadora2);
        comisionDictaminadora1.setId(null);
        assertThat(comisionDictaminadora1).isNotEqualTo(comisionDictaminadora2);
    }
}
