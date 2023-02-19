package com.example.security.repositories;

import com.example.security.models.factory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface factoryRepository extends JpaRepository<factory,String> {

    void deleteAllById(String id);


}
