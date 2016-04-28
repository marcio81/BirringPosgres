package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Cervesa;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Cervesa entity.
 */
public interface CervesaRepository extends JpaRepository<Cervesa,Long> {

}
