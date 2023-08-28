package com.uady.saicc.repository;

import com.uady.saicc.domain.Puesto;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Puesto entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PuestoRepository extends JpaRepository<Puesto, Long> {}
