package com.uady.saicc.repository;

import com.uady.saicc.domain.CentroDocente;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the CentroDocente entity.
 */
@Repository
public interface CentroDocenteRepository extends JpaRepository<CentroDocente, Long> {
    default Optional<CentroDocente> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<CentroDocente> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<CentroDocente> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select distinct centroDocente from CentroDocente centroDocente left join fetch centroDocente.comisionDictaminadora",
        countQuery = "select count(distinct centroDocente) from CentroDocente centroDocente"
    )
    Page<CentroDocente> findAllWithToOneRelationships(Pageable pageable);

    @Query("select distinct centroDocente from CentroDocente centroDocente left join fetch centroDocente.comisionDictaminadora")
    List<CentroDocente> findAllWithToOneRelationships();

    @Query(
        "select centroDocente from CentroDocente centroDocente left join fetch centroDocente.comisionDictaminadora where centroDocente.id =:id"
    )
    Optional<CentroDocente> findOneWithToOneRelationships(@Param("id") Long id);
}
