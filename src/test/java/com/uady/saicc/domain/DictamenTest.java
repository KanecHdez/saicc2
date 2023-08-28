package com.uady.saicc.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.uady.saicc.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DictamenTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Dictamen.class);
        Dictamen dictamen1 = new Dictamen();
        dictamen1.setId(1L);
        Dictamen dictamen2 = new Dictamen();
        dictamen2.setId(dictamen1.getId());
        assertThat(dictamen1).isEqualTo(dictamen2);
        dictamen2.setId(2L);
        assertThat(dictamen1).isNotEqualTo(dictamen2);
        dictamen1.setId(null);
        assertThat(dictamen1).isNotEqualTo(dictamen2);
    }
}
