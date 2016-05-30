package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Cervesa;
import com.mycompany.myapp.domain.Evaluar;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the Cervesa entity.
 */
public interface CervesaRepository extends JpaRepository<Cervesa,Long> {

}
