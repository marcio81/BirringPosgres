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
    @Query("SELECT comentario FROM Comentario comentario left join fetch comentario.cervesa")
    List<Comentario> findComentario();
}
