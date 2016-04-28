package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Ubicacion;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the Ubicacion entity.
 */
public interface UbicacionRepository extends JpaRepository<Ubicacion,Long> {

    @Query("select distinct ubicacion from Ubicacion ubicacion left join fetch ubicacion.cervesas")
    List<Ubicacion> findAllWithEagerRelationships();

    @Query("select ubicacion from Ubicacion ubicacion left join fetch ubicacion.cervesas where ubicacion.id =:id")
    Ubicacion findOneWithEagerRelationships(@Param("id") Long id);

}
