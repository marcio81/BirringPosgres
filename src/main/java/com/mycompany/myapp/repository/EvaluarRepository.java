package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Evaluar;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Evaluar entity.
 */
public interface EvaluarRepository extends JpaRepository<Evaluar,Long> {

    @Query("select evaluar from Evaluar evaluar where evaluar.user.login = ?#{principal.username}")
    List<Evaluar> findByUserIsCurrentUser();

    @Query("SELECT AVG(e.evaluacion), e.cervesa.id FROM Evaluar e group by e.cervesa.id")
    List<Object[]> findTop10();
}
