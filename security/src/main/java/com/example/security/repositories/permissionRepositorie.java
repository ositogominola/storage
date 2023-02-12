package com.example.security.repositories;

import com.example.security.models.permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface permissionRepositorie extends JpaRepository<permission, String> {

    Boolean existsByurl(String name);

}
