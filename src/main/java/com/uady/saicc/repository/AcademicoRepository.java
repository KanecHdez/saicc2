package com.uady.saicc.repository;

import com.uady.saicc.domain.Academico;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Academico entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AcademicoRepository extends JpaRepository<Academico, Long> {}
