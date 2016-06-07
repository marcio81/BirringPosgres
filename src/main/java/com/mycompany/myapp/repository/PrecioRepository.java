package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Precio;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the Precio entity.
 */
public interface PrecioRepository extends JpaRepository<Precio,Long> {

    @Query("select precio from Precio precio where precio.user.login = ?#{principal.username}")
    List<Precio> findByUserIsCurrentUser();

    //Query para obtener todos los precios asociados a una cerveza NO ES LIST?? NO SE NECESITA AVG? order by  AVG(p.precio)
    @Query("select AVG(p.precio) from Precio p where p.cervesa.id=:id ")
    List<Precio> findPrecioMedio(@Param("id") Long id);
}
