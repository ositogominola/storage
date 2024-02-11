package com.example.security.repositories;

import com.example.security.models.roles;
import org.hibernate.service.spi.InjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@Service
public class classRolRepo{

    @Autowired
    private rolesRepositorie rlp;

    public roles findroltByname(String name) {
        return rlp.findByname(name).get();
    }
}
