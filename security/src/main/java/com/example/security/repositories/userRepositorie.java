package com.example.security.repositories;

import com.example.security.models.user;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface userRepositorie extends JpaRepository<user, UUID> {
    Boolean existsByEmail(String email);

    Optional<user> findByUsername(String username);

    @Query("SELECT u.idUser, u.name, u.lastname, u.email, r.name, r.idRol FROM user u JOIN u.roles r WHERE u.username = ?1")
    List<Object[]> findUserByUsername(String username);

    Boolean existsByUsername(String username);
}
