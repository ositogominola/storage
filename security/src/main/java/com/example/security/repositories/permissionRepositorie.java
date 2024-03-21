package com.example.security.repositories;

import com.example.security.models.permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Optional;

@Repository
public interface permissionRepositorie extends JpaRepository<permission, String> {

    Boolean existsByurl(String name);

    Optional<permission> findByUrl(String name);

    @Query(value = "SELECT p.id_permission, p.url, p.method, p.nombre, p.grupo_permiso " +
            "FROM permission p " +
            "LEFT JOIN permission_asignados pa ON p.id_permission = pa.permission_id AND pa.roles_id = ?2 " +
            "WHERE pa.permission_id IS NULL AND p.recurso_id = ?1 and p.pert_recurso=1",
            nativeQuery = true)
    ArrayList<String[]> getPermisionRecursoRolmissed(int idRecurso, int idRol);

    @Query(value = "SELECT CASE " +
            "WHEN EXISTS(SELECT * FROM perfiles_items pi WHERE pi.url_front LIKE :recurso) THEN " +
            "(SELECT CONCAT('', ',', pi.nombre) FROM perfiles_items pi WHERE pi.url_front LIKE :recurso) " +
            "WHEN EXISTS(SELECT * FROM recursos r WHERE r.url_front LIKE :recurso) THEN " +
            "(SELECT CONCAT( IF(pi.url_front='ItemsUserPerfil', CONCAT(pi.url_front, '/', r.perfil_item_id,'/', :idRol), pi.url_front) ,',', pi.nombre, ',', r.url_front, ',', r.name_front) as ruta FROM recursos r " +
            "INNER JOIN perfiles_items pi ON r.perfil_item_id=pi.id_perfil " +
            "WHERE r.url_front LIKE :recurso) " +
            "ELSE 'no existe' " +
            "END AS resultado; " , nativeQuery = true)
    String[] getDireccionesRuta(@Param("recurso") String Recurso, @Param("idRol") int idRol);


}
