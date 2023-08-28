package com.uady.saicc.repository;

import com.uady.saicc.domain.ComisionDictaminadora;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the ComisionDictaminadora entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ComisionDictaminadoraRepository extends JpaRepository<ComisionDictaminadora, Long> {}
