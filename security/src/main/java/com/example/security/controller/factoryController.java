package com.example.security.controller;

import com.example.security.models.factory;
import com.example.security.models.user;
import com.example.security.repositories.factoryRepository;
import com.example.security.repositories.userRepositorie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/factory")
public class factoryController {

    @Autowired
    userRepositorie rpuser;

    @Autowired
    factoryRepository rpFact;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/create")
    public String create(Authentication auth, @RequestBody factory ownfactory){

        user usuario=rpuser.findByUsername(auth.getName()).get();

        if (usuario !=null){

            ownfactory.setUsuario(usuario);
            rpFact.save(ownfactory);
        }

        return ownfactory.getId()+": id de la empresa";
    }

    @GetMapping("/getall")
    public ResponseEntity<Set<factory>> getall(Authentication auth){
        user usuario=rpuser.findByUsername(auth.getName()).get();
        for (factory f: usuario.getFactorys()) {
            System.out.println(f);
        }
        return ResponseEntity.ok(usuario.getFactorys());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteFactory(@PathVariable(value = "id") String id, Authentication auth) {
        Optional<factory> factoryOpt = rpFact.findById(id);
        if (factoryOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("La fábrica no existe");
        }
        factory factory = factoryOpt.get();
        if (!factory.getUsuario().getUsername().equals(auth.getName())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("No tienes permiso para eliminar esta fábrica");
        }
        rpFact.delete(factory);
        return ResponseEntity.ok("Fábrica eliminada correctamente");
    }

}
