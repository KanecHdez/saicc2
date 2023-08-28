package com.uady.saicc.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.uady.saicc.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TabuladorPromocionTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TabuladorPromocion.class);
        TabuladorPromocion tabuladorPromocion1 = new TabuladorPromocion();
        tabuladorPromocion1.setId(1L);
        TabuladorPromocion tabuladorPromocion2 = new TabuladorPromocion();
        tabuladorPromocion2.setId(tabuladorPromocion1.getId());
        assertThat(tabuladorPromocion1).isEqualTo(tabuladorPromocion2);
        tabuladorPromocion2.setId(2L);
        assertThat(tabuladorPromocion1).isNotEqualTo(tabuladorPromocion2);
        tabuladorPromocion1.setId(null);
        assertThat(tabuladorPromocion1).isNotEqualTo(tabuladorPromocion2);
    }
}
