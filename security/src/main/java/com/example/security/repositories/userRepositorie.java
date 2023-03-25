package com.example.security.repositories;

import com.example.security.models.user;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface userRepositorie extends JpaRepository<user, UUID> {
    Boolean existsByEmail(String email);

    Optional<user> findByUsername(String username);
    Boolean existsByUsername(String username);

}
