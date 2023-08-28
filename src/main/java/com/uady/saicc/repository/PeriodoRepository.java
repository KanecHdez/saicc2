package com.uady.saicc.repository;

import com.uady.saicc.domain.Periodo;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Periodo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PeriodoRepository extends JpaRepository<Periodo, Long> {}
