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

import java.util.HashMap;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@RestController
@RequestMapping("/factory")
public class factoryController {
    @Autowired
    userRepositorie rpuser;
    @Autowired
    factoryRepository rpFact;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/create")
    public HashMap<String, Object> create(Authentication auth, @RequestBody factory ownfactory){
        HashMap<String, Object> response=new HashMap<String,Object>();
        user usuario=rpuser.findByUsername(auth.getName()).get();

        if (usuario !=null){
            ownfactory.setUsuario(usuario);
            rpFact.save(ownfactory);
            ownfactory=rpFact.save(ownfactory);
            response.put("factory",ownfactory);
            response.put("message","creacion exitosa");
            response.put("successful",true);
        }
        else {
            response.put("factory",ownfactory);
            response.put("message","no fue posible crear la empresa");
            response.put("successful",true);
        }
        return response;
    }

    @GetMapping("/getall")
    public HashMap<String, Object> getall(Authentication auth){
        HashMap<String,Object> response=new HashMap<String,Object>();
        user usuario=rpuser.findByUsername(auth.getName()).get();
        if (usuario != null){
            if (!(usuario.getFactorys().isEmpty())){
                response.put("factory",usuario.getFactorys());
                response.put("message","empresas cargadas");
                response.put("successful",true);
            }
            else{
                response.put("factory",null);
                response.put("message","no a registrado ninguna empresa");
                response.put("successful",false);
            }

        }
        else {
            response.put("factory",usuario.getFactorys());
            response.put("message","no se a logeado");
            response.put("successful",false);
        }


        return response;
    }

    @DeleteMapping("/delete/{id}")
    public HashMap<String,Object> deleteFactory(@PathVariable(value = "id") UUID id, Authentication auth) {
        HashMap<String,Object> response=new HashMap<String,Object>();
        Optional<factory> factoryOpt = rpFact.findById(id);
        if (factoryOpt.isEmpty()) {
            response.put("message","la fabrica no existe");
            response.put("successful",false);
        }
        else {
            factory factory = factoryOpt.get();
            if (!factory.getUsuario().getUsername().equals(auth.getName())) {
                response.put("message","No tienes permiso para eliminar esta fábrica");
                response.put("successful",false);
            }
            else {
                rpFact.delete(factory);
                response.put("message","Fábrica eliminada correctamente");
                response.put("successful",true);
            }
        }

        return response;
    }

    @PatchMapping("/update/{id}")
    public HashMap<String,Object> updateFactory(@PathVariable(value = "id") UUID id, @RequestBody factory fc, Authentication auth) {
        HashMap<String,Object> response=new HashMap<String,Object>();
        try {
            Optional<factory> factoryOpt = rpFact.findById(id);

            if (factoryOpt.isEmpty()) {
                response.put("factory",null);
                response.put("message","La fábrica no existe");
                response.put("successful",false);
            }
            else {
                factory factory = factoryOpt.get();

                if (!factory.getUsuario().getUsername().equals(auth.getName())) {
                    response.put("factory",null);
                    response.put("message","No tienes permiso para actualizar esta empresa");
                    response.put("successful",false);
                }
                else
                {
                    factory.setNombre((fc.getNombre()!=null) ? fc.getNombre() : factory.getNombre() );
                    factory.setDireccion((fc.getDireccion()!=null) ? fc.getDireccion() : factory.getDireccion());
                    factory.setLogo((fc.getLogo()!=null) ? fc.getLogo() : factory.getLogo());
                    rpFact.save(factory);
                    response.put("factory",factory);
                    response.put("message","Fábrica actualizada correctamente");
                    response.put("successful",true);
                }


            }

        } catch (Exception e) {
            response.put("factory",null);
            response.put("message","error: "+e);
            response.put("successful",false);
        }
        return response;
    }


    @GetMapping("/getByid/{id}")
    public HashMap<String,Object> getbyid(@PathVariable UUID id, Authentication auth){
        HashMap<String,Object> response=new HashMap<String,Object>();
        Optional<factory> ownerFactory = this.rpFact.findById(id);

        if (ownerFactory.isPresent())
        {
            if (ownerFactory.get().getUsuario().getUsername().equals(auth.getName())){
                response.put("factory",ownerFactory.get());
                response.put("message","empresa cargada");
                response.put("seccessful",true);
            }
            else
            {
                response.put("factory",null);
                response.put("message","La fábrica no existe o no tienes permiso para acceder a ella");
                response.put("seccessful",false);
            }
        }
        else {
            response.put("factory",null);
            response.put("message","La fábrica no existe o no tienes permiso para acceder a ella");
            response.put("seccessful",false);
        }
        return response;
    }

}
