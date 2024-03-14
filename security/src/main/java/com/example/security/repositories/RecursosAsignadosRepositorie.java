package com.example.security.repositories;

import com.example.security.models.RecursosAsignados;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@Repository
public interface RecursosAsignadosRepositorie extends JpaRepository<RecursosAsignados, Integer> {

    @Query(value = "select r.id_recurso, r.name_front, r.url_front, r.color_front, r.icon from recursos_asignados ra " +
            "inner join recursos r on r.id_recurso=ra.recursos_id " +
            "where r.perfil_item_id=?1 and ra.roles_id=?2",
            nativeQuery = true)
    ArrayList<String[]> findByperfilItemRecursosAndRol(int id, int idrol);

    @Transactional
    @Modifying
    @Query(value = "delete from recursos_asignados where roles_id=?1", nativeQuery = true)
    int deleteByIdR(int id);

    @Transactional
    @Modifying
    @Query(value = "delete from recursos_asignados where roles_id=?1 and recursos_id in (select id_recurso from recursos where perfil_item_id=?2)", nativeQuery = true)
    int deleteByIdRolAndIdPerfil(int idRol, int idPerfil);

    @Transactional
    @Modifying
    @Query(value = "delete from recursos_asignados where roles_id=?1 and recursos_id=?2", nativeQuery = true)
    int deleteRecursoRol(int idRol, int idRecurso);
}
