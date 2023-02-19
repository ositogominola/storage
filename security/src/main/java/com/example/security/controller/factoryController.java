package com.example.security.controller;

import com.example.security.models.factory;
import com.example.security.models.user;
import com.example.security.repositories.factoryRepository;
import com.example.security.repositories.userRepositorie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
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



            //

        }

        return ownfactory.getId()+": id de la empresa";
    }

    @GetMapping("/getall")
    public Set<factory> getall(Authentication auth){
        user usuario=rpuser.findByUsername(auth.getName()).get();
        for (factory f: usuario.getFactorys()) {
            System.out.println(f);
        }
        return  usuario.getFactorys();
    }

    @DeleteMapping("/delete/{id}")
    public String deletefactory(@PathVariable(value = "id") String id,Authentication auth)
    {
        Optional<factory> fc=rpFact.findById(id);
        System.out.println(fc);

        System.out.println(auth.getName());
        Optional<user> us=rpuser.findByUsername(auth.getName());

        System.out.println("usuario: "+us+" factory: "+fc );
        if (!us.isPresent() || !fc.isPresent()) {
            return "la empresa no existe";

        }
        else {
            System.out.println("llego aqui");
            if (fc.get().getUsuario().getIdUser()==us.get().getIdUser()) {
                rpFact.deleteById(id);
                return "empresa eliminada";
            }
        }
        return "no funciono";
    }

}
