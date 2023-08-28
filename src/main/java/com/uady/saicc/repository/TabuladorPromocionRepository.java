package com.uady.saicc.repository;

import com.uady.saicc.domain.TabuladorPromocion;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the TabuladorPromocion entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TabuladorPromocionRepository extends JpaRepository<TabuladorPromocion, Long> {}
