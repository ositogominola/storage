package com.example.security.repositories;

import com.example.security.models.roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Optional;

@Repository
public interface rolesRepositorie extends JpaRepository<roles, String> {
    Boolean existsByname(String name);
    Optional<roles> findByname(String name);

}
