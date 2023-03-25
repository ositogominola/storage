package com.example.security.repositories;

import com.example.security.models.factory;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface factoryRepository extends JpaRepository<factory, UUID> {


}
