package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Precio;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Precio entity.
 */
public interface PrecioRepository extends JpaRepository<Precio,Long> {

    @Query("select precio from Precio precio where precio.user.login = ?#{principal.username}")
    List<Precio> findByUserIsCurrentUser();

}
