package com.uady.saicc.repository;

import com.uady.saicc.domain.ActividadProducto;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the ActividadProducto entity.
 */
@Repository
public interface ActividadProductoRepository extends JpaRepository<ActividadProducto, Long> {
    default Optional<ActividadProducto> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<ActividadProducto> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<ActividadProducto> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select distinct actividadProducto from ActividadProducto actividadProducto left join fetch actividadProducto.tabuladorActProd left join fetch actividadProducto.dictamen",
        countQuery = "select count(distinct actividadProducto) from ActividadProducto actividadProducto"
    )
    Page<ActividadProducto> findAllWithToOneRelationships(Pageable pageable);

    @Query(
        "select distinct actividadProducto from ActividadProducto actividadProducto left join fetch actividadProducto.tabuladorActProd left join fetch actividadProducto.dictamen"
    )
    List<ActividadProducto> findAllWithToOneRelationships();

    @Query(
        "select actividadProducto from ActividadProducto actividadProducto left join fetch actividadProducto.tabuladorActProd left join fetch actividadProducto.dictamen where actividadProducto.id =:id"
    )
    Optional<ActividadProducto> findOneWithToOneRelationships(@Param("id") Long id);
}
