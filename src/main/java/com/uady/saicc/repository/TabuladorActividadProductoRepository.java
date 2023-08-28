package com.uady.saicc.repository;

import com.uady.saicc.domain.TabuladorActividadProducto;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the TabuladorActividadProducto entity.
 */
@Repository
public interface TabuladorActividadProductoRepository extends JpaRepository<TabuladorActividadProducto, Long> {
    default Optional<TabuladorActividadProducto> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<TabuladorActividadProducto> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<TabuladorActividadProducto> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select distinct tabuladorActividadProducto from TabuladorActividadProducto tabuladorActividadProducto left join fetch tabuladorActividadProducto.tabuladorActSuperior",
        countQuery = "select count(distinct tabuladorActividadProducto) from TabuladorActividadProducto tabuladorActividadProducto"
    )
    Page<TabuladorActividadProducto> findAllWithToOneRelationships(Pageable pageable);

    @Query(
        "select distinct tabuladorActividadProducto from TabuladorActividadProducto tabuladorActividadProducto left join fetch tabuladorActividadProducto.tabuladorActSuperior"
    )
    List<TabuladorActividadProducto> findAllWithToOneRelationships();

    @Query(
        "select tabuladorActividadProducto from TabuladorActividadProducto tabuladorActividadProducto left join fetch tabuladorActividadProducto.tabuladorActSuperior where tabuladorActividadProducto.id =:id"
    )
    Optional<TabuladorActividadProducto> findOneWithToOneRelationships(@Param("id") Long id);
}
