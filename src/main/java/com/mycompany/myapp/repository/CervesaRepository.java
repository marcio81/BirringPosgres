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
    // PARAM PARTE VARIABLE DE UN SELECT
    /*@Query("select c from Cervesa c  order by c.evaluars desc ")// : objeto param NO VA
    List<Cervesa> TopCervezas();*/
    //@Query("select c from Cervesa c group by c.evaluars order by COUNT(c.evaluars) desc ")

   // @Query("select c from Cervesa c ") //este funciona
    //@Query("select c from Cervesa c, Evaluar ev where c.evaluars = ev.id")
   // Page<Cervesa> findByTopCervesas(Pageable topTen);

    //@Query("select c from Cervesa c group by c.evaluars order by COUNT(c.evaluars) desc ")
    //Page<Cervesa> findByTopCervesas(Pageable var1);

   // List<Cervesa> TopCervezas(@Param("top") Integer top);//"topcervezas" es el que comprueba con la query
   @Query("SELECT cervesaName, foto, tipo FROM Cervesa where cervesaName = :cervesaName")
  List<Cervesa> findAllCerva(@Param("cervesaName") String cervesaName);
}
