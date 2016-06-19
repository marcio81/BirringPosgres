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


     /*@Query("SELECT e.cervesa.id, e.cervesa.cervesaName, e.cervesa.foto, AVG(e.evaluacion)  FROM Evaluar e group by e.cervesa.id, e.cervesa.cervesaName, e.cervesa.foto order by  AVG(e.evaluacion) desc")
    Page<Object[]> findTop10(Pageable pageable);

    @Query("SELECT j from Jugador j WHERE j.equipo.id=:id order by j.rebotesTotales asc")
    List<Jugador> findByEquipoOrderByRebotesTotalesAsc(@Param("id") Long id, Pageable pageable);*/

    /*@Query("select c from Comentario c  where c.cervesa.id=:id")
    List<Comentario> findComentarioID(@Param("id") Long id);*/

    //El cervesas hace referencia al mappedBy
    @Query("select distinct u from Ubicacion u JOIN u.cervesas p  where p.id=:id")
    List<Ubicacion> findUbicacionesID(@Param("id") Long id);

    //El cervesas hace referencia al mappedBy
    @Query("select distinct u , a from Ubicacion u, Precio a JOIN u.cervesas p  where p.id=:id and a.ubicacion.id=u.id")
    List<Object[]> findUbicaciones2(@Param("id") Long id);



}
