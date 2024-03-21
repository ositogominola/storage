package com.example.security.repositories;

import com.example.security.models.PermissionAsignados;
import com.example.security.models.roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
@Repository
public interface PermissionAsignadosRepositorie extends JpaRepository<PermissionAsignados, String> {

    @Query(value = "select p.url, p.method from permission_asignados pa " +
            "inner join permission p on pa.permission_id=p.id_permission " +
            "where pa.roles_id=?1 ",
            nativeQuery = true)
    List<String[]> findPermisosByid(String idRp);

    @Query(value = "select p.id_permission, p.url, p.method, p.nombre from permission_asignados pa " +
            "inner join permission p on pa.permission_id=p.id_permission " +
            "where pa.roles_id=?1 and p.recurso_id=?2",
            nativeQuery = true)
    List<String[]> findPermisosByRecurso(int idRol, int idRec);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM permission_asignados WHERE roles_id = ?1", nativeQuery = true)
    int deleteByIdR(int id);

    @Transactional
    @Modifying
    @Query(value = "delete from permission_asignados where roles_id=?1 and permission_id in " +
            "(select p.id_permission from permission p " +
            "inner join  recursos r on p.recurso_id=r.id_recurso " +
            "where r.perfil_item_id=?2 " +
            ");", nativeQuery = true)
    int deleteByIdRAndPerfil(int idRol, int idPerfil);

    @Transactional
    @Modifying
    @Query(value = "insert into permission_asignados(permission_id, roles_id) " +
            "select id_permission, ?1 from permission where grupo_permiso=?2", nativeQuery = true)
    int addPermisoGroup(int idRol, int idGroup);

    @Transactional
    @Modifying
    @Query(value = "insert into permission_asignados(permission_id, roles_id) " +
            "select id_permission, ?1 from permission where permiso_requerido=1", nativeQuery = true)
    int addPermisosRequeridos(int idRol);

    @Transactional
    @Modifying
    @Query(value = "delete from permission_asignados where roles_id=?1 and permission_id in " +
            "(select p.id_permission from permission p " +
            "left join  recursos r on p.recurso_id=r.id_recurso " +
            "where (r.id_recurso=?2 or p.grupo_permiso in (select distinct grupo_permiso from permission where recurso_id=?2)))"
            , nativeQuery = true)
    int deletePermisosByRolAndRecurso(int idRol, int idRecurso);

    @Transactional
    @Modifying
    @Query(value = "delete from permission_asignados where roles_id=?1 and permission_id=?2", nativeQuery = true)
    int deletePermiso(int idRol,int idPermiso);

    @Query(value = "select p.id_permission, p.nombre from permission_asignados pa " +
            "inner join permission p on p.id_permission=pa.permission_id " +
            "inner join recursos r on r.id_recurso=p.recurso_id "+
            "where pa.roles_id=?1 and r.url_front = ?2 ", nativeQuery = true)
    List<String[]> getPermisosRecurso(int idRol, String idRecursos);
}
