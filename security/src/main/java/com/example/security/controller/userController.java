package com.example.security.controller;

import com.example.security.models.permission;
import com.example.security.models.roles;
import com.example.security.models.user;
import com.example.security.repositories.permissionRepositorie;
import com.example.security.repositories.rolesRepositorie;
import com.example.security.repositories.userRepositorie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/user")
public class userController {

    @Autowired
    private userRepositorie ur;

    @Autowired
    private rolesRepositorie rlr;

    @Autowired
    private permissionRepositorie pr;


    //crear permiso
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/createPermissions")
    public String create_permission(@RequestBody permission per){
        if (this.pr.existsByurl(per.getUrl())){

            return "el permiso ya exise";
        }
        else {
            this.pr.save(per);
            return "id: "+per.getIdPermission()+" use este id para asignaciones";
        }
    }

    //añadir permiso a rol
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/addpermission/{idper}/rol/{idrol}")
    public String addpermission(@PathVariable(value = "idper") String idper,@PathVariable(value = "idrol") String idrol){

        Optional<roles> rolown =this.rlr.findById(idrol);
        Optional<permission> perm=this.pr.findById(idper);

        if (perm.isEmpty() || rolown.isEmpty()){
            return "uno de los datos no existe";
        }
        else {
            roles rl= rolown.get();
            permission prm= perm.get();
            rl.addPermission(prm);
            this.rlr.save(rl);
            return idrol+"";
        }
    }

    //crear rol
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/createRole")
    public String create_rol(@RequestBody roles rol){
        if (this.rlr.existsByname(rol.getName())){
            return "el rol ya exise";
        }
        else {
            this.rlr.save(rol);
            return "id rol: "+rol.getIdRol()+" nombre: "+rol.getName();
        }
    }

    //crear usuario
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/create")
    public String create_user (@RequestBody user infoUsuario){

        if ((this.ur.existsByEmail(infoUsuario.getEmail()) || (this.ur.existsByUsername(infoUsuario.getUsername()))))
        {
            return "el usuario o correo ya existe";
        }
        else {
            if (infoUsuario.is_valid()){
                infoUsuario.setPassword(passwordEncoder.encode(infoUsuario.getPassword()));
                infoUsuario.setEnabled(Boolean.TRUE);
                this.ur.save(infoUsuario);
                return "usario creado";
            }
            else {
                return "ingrese todos los datos";
            }
        }
    }

    @GetMapping
    public user prueba(Authentication authentication){

        user owneruser=ur.findByUsername(authentication.getName()).get();
        owneruser.setPassword("");

        return owneruser;
    }

    @Autowired
    private PasswordEncoder passwordEncoder;

}
