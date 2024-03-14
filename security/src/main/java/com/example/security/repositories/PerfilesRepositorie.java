package com.example.security.repositories;

import com.example.security.models.perfiles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface PerfilesRepositorie extends JpaRepository<perfiles,Integer> {


}

