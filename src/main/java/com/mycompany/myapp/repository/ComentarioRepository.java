package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Comentario;
import com.mycompany.myapp.domain.Cervesa;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.List;

/**
 * Spring Data JPA repository for the Comentario entity.
 */
public interface ComentarioRepository extends JpaRepository<Comentario,Long> {

    @Query("select comentario from Comentario comentario where comentario.user.login = ?#{principal.username}")
    List<Comentario> findByUserIsCurrentUser();
    /*@Query("SELECT comentario FROM Comentario comentario left join fetch comentario.cervesa")
    List<Comentario> findComentario();*/


    /*@Query("SELECT e.cervesa.id, e.cervesa.cervesaName, e.cervesa.foto, AVG(e.evaluacion)  FROM Evaluar e group by e.cervesa.id, e.cervesa.cervesaName, e.cervesa.foto order by  AVG(e.evaluacion) desc")
    Page<Object[]> findTop10(Pageable pageable);

    @Query("SELECT j from Jugador j WHERE j.equipo.id=:id order by j.rebotesTotales asc")
    List<Jugador> findByEquipoOrderByRebotesTotalesAsc(@Param("id") Long id, Pageable pageable);*/

    @Query("select c from Comentario c  where c.cervesa.id=:id")
    List<Comentario> findComentarioID(@Param("id") Long id);

}
