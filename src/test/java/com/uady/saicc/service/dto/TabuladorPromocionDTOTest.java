package com.uady.saicc.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.uady.saicc.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TabuladorPromocionDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TabuladorPromocionDTO.class);
        TabuladorPromocionDTO tabuladorPromocionDTO1 = new TabuladorPromocionDTO();
        tabuladorPromocionDTO1.setId(1L);
        TabuladorPromocionDTO tabuladorPromocionDTO2 = new TabuladorPromocionDTO();
        assertThat(tabuladorPromocionDTO1).isNotEqualTo(tabuladorPromocionDTO2);
        tabuladorPromocionDTO2.setId(tabuladorPromocionDTO1.getId());
        assertThat(tabuladorPromocionDTO1).isEqualTo(tabuladorPromocionDTO2);
        tabuladorPromocionDTO2.setId(2L);
        assertThat(tabuladorPromocionDTO1).isNotEqualTo(tabuladorPromocionDTO2);
        tabuladorPromocionDTO1.setId(null);
        assertThat(tabuladorPromocionDTO1).isNotEqualTo(tabuladorPromocionDTO2);
    }
}
