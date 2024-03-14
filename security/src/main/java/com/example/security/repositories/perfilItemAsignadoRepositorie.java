package com.example.security.repositories;

import com.example.security.models.perfilesItemsAsignados;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface perfilItemAsignadoRepositorie extends JpaRepository<perfilesItemsAsignados, Integer> {

    @Transactional
    @Modifying
    @Query(value = "delete from perfiles_items_asignados where roles_id=?1", nativeQuery = true)
    int deleteByIdR(int id);

    @Transactional
    @Modifying
    @Query(value = "delete from perfiles_items_asignados where roles_id=?1 and perfiles_items_id =?2", nativeQuery = true)
    int deleteByIdAndRolId(int idRol, int idPerfil);
}
