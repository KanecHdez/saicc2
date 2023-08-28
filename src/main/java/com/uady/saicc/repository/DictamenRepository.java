package com.uady.saicc.repository;

import com.uady.saicc.domain.Dictamen;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Dictamen entity.
 */
@Repository
public interface DictamenRepository extends JpaRepository<Dictamen, Long> {
    default Optional<Dictamen> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<Dictamen> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<Dictamen> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select distinct dictamen from Dictamen dictamen left join fetch dictamen.academico left join fetch dictamen.puestoActual left join fetch dictamen.puestoSolicitado left join fetch dictamen.periodo left join fetch dictamen.comisionDictaminadora left join fetch dictamen.dependencia",
        countQuery = "select count(distinct dictamen) from Dictamen dictamen"
    )
    Page<Dictamen> findAllWithToOneRelationships(Pageable pageable);

    @Query(
        "select distinct dictamen from Dictamen dictamen left join fetch dictamen.academico left join fetch dictamen.puestoActual left join fetch dictamen.puestoSolicitado left join fetch dictamen.periodo left join fetch dictamen.comisionDictaminadora left join fetch dictamen.dependencia"
    )
    List<Dictamen> findAllWithToOneRelationships();

    @Query(
        "select dictamen from Dictamen dictamen left join fetch dictamen.academico left join fetch dictamen.puestoActual left join fetch dictamen.puestoSolicitado left join fetch dictamen.periodo left join fetch dictamen.comisionDictaminadora left join fetch dictamen.dependencia where dictamen.id =:id"
    )
    Optional<Dictamen> findOneWithToOneRelationships(@Param("id") Long id);
}
