package com.example.security.repositories;

import com.example.security.models.permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
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


}
