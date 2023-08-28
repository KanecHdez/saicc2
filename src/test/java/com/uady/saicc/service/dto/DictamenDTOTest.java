package com.uady.saicc.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.uady.saicc.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DictamenDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DictamenDTO.class);
        DictamenDTO dictamenDTO1 = new DictamenDTO();
        dictamenDTO1.setId(1L);
        DictamenDTO dictamenDTO2 = new DictamenDTO();
        assertThat(dictamenDTO1).isNotEqualTo(dictamenDTO2);
        dictamenDTO2.setId(dictamenDTO1.getId());
        assertThat(dictamenDTO1).isEqualTo(dictamenDTO2);
        dictamenDTO2.setId(2L);
        assertThat(dictamenDTO1).isNotEqualTo(dictamenDTO2);
        dictamenDTO1.setId(null);
        assertThat(dictamenDTO1).isNotEqualTo(dictamenDTO2);
    }
}
