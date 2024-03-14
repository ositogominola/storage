package com.example.security.repositories;

import com.example.security.models.Recursos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
@Repository
public interface recursosRepositorie extends JpaRepository<Recursos, Integer> {

    @Query(value = "select p.url, p.method " +
            "from perfiles_items pi  " +
            "inner join roles_perfiles prf on pi.id_perfil=prf.perfiles_id " +
            "inner join user ur on ur.roles_id_rol=prf.roles_id " +
            "inner join recursos r on pi.id_perfil=r.perfil_item_id " +
            "inner join permission p on p.recurso_id=r.id_recurso " +
            "where prf.roles_id=?1",
            nativeQuery = true)
    List<String[]> findPermisosByrol(String idRol);

    @Query(value = "select r.id_recurso, r.name_front, r.url_front, r.color_front, r.icon from recursos r " +
            "inner join recursos_asignados ra on ra.recursos_id=r.id_recurso and ra.roles_id=?2 " +
            "where perfil_item_id=?1",nativeQuery = true)
    ArrayList<String[]> findByperfilItem(int id, int idRol);

    @Query(value = "select r.id_recurso, r.name_front, r.url_front, r.color_front, r.icon from recursos r where perfil_item_id=?1",nativeQuery = true)
    ArrayList<String[]> findByperfilItemRecursos(int id);

    @Query(value = "SELECT r.id_recurso, r.name_front, r.url_front " +
            "FROM recursos r " +
            "LEFT JOIN recursos_asignados ra ON r.id_recurso = ra.recursos_id AND ra.roles_id = ?1 " +
            "WHERE ra.recursos_id IS NULL AND r.perfil_item_id = ?2 ", nativeQuery = true)
    ArrayList<String[]> findFaltantesPerfilItem(int idRol, int idPerfil);
}
