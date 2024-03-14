package com.example.security.repositories;

import com.example.security.models.PerfilesItems;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
public interface perfilesItemsRepositorie extends JpaRepository<PerfilesItems, Integer> {

    @Query(value =
            "SELECT p.nombre, pt.nombre, pt.icon, pt.url_front, pt.id_perfil, r.id_rol " +
                    "FROM  perfiles_items pt " +
                    "INNER JOIN perfiles p  ON pt.perfiles_id = p.id_perfil " +
                    "left join perfiles_items_asignados pia on pia.perfiles_items_id= pt.id_perfil " +
                    "inner join roles r on r.id_rol=pia.roles_id " +
                    "where pia.roles_id=?1",
            nativeQuery = true)
    List<String[]> PerfilesRecurosUsuario(int id);

    @Query(value = "SELECT p.nombre, pt.nombre, pt.icon, pt.url_front, pt.id_perfil " +
            "FROM perfiles p " +
            "INNER JOIN perfiles_items pt ON pt.perfiles_id = p.id_perfil " +
            "inner join roles_perfiles rp on rp.perfiles_id=p.id_perfil " +
            "inner join perfiles_items_asignados pia on pia.perfiles_items_id=pt.id_perfil " +
            "where pia.roles_id=?1", nativeQuery = true)
    List<String[]> PerfilesItemsByRol(String idRol);

    @Query(value = "SELECT pi.id_perfil, pi.nombre, pi.url_front, p.nombre " +
            "FROM perfiles_items pi " +
            "inner join perfiles p on p.id_perfil=pi.perfiles_id "+
            "WHERE pi.id_perfil NOT IN (" +
            "    SELECT pia.perfiles_items_id " +
            "    FROM perfiles_items_asignados pia " +
            "    WHERE pia.roles_id = ?1" +
            ") order by p.nombre",
            nativeQuery = true)
    ArrayList<String[]> getPerfilesRolFaltante(int idRol);
}
