package com.example.security.controller;

import com.example.security.models.roles;
import com.example.security.repositories.classRolRepo;
import com.example.security.repositories.rolesRepositorie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cnf")
public class comprobacion {

    @Autowired
    private classRolRepo rlp;

    @GetMapping("/getRol/{name}")
    public roles getrol(@PathVariable(value = "name") String name){
        return rlp.findroltByname(name);
    }

}
