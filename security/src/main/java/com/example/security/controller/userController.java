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

import java.util.HashMap;
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
    public HashMap create_permission(@RequestBody permission per){
        HashMap<String,Object> response=new HashMap<String,Object>();
        if (this.pr.existsByurl(per.getUrl())){
            response.put("Permissions",null);
            response.put("message","el permiso ya exise");
            response.put("successful",false);
        }
        else {
            this.pr.save(per);
            response.put("Permissions",per);
            response.put("message","id: "+per.getIdPermission()+" use este id para asignaciones");
            response.put("successful",true);
        }
        return response;
    }

    //añadir permiso a rol
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/addpermission/{idper}/rol/{idrol}")
    public HashMap addpermission(@PathVariable(value = "idper") String idper,@PathVariable(value = "idrol") String idrol){
        HashMap<String,Object> response = new HashMap<String,Object>();
        Optional<roles> rolown =this.rlr.findById(idrol);
        Optional<permission> perm=this.pr.findById(idper);

        if (perm.isEmpty() || rolown.isEmpty()){
            response.put("message","uno de los datos no existe");
            response.put("successful",true);
        }
        else {
            roles rl= rolown.get();
            permission prm= perm.get();
            rl.addPermission(prm);
            this.rlr.save(rl);
            response.put("message","permiso agregado con exito");
            response.put("successful",true);
        }
        return response;
    }

    //crear rol
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/createRole")
    public HashMap create_rol(@RequestBody roles rol){
        HashMap<String,Object> response=new HashMap<String,Object>();
        if (this.rlr.existsByname(rol.getName())){
            response.put("rol",null);
            response.put("message","el rol ya exise");
            response.put("successful",false);
        }
        else {
            response.put("rol",rol);
            response.put("message","id rol: "+rol.getIdRol()+" nombre: "+rol.getName());
            response.put("successful",true);
            this.rlr.save(rol);
        }
        return response;
    }

    //crear usuario
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/create")
    public HashMap create_user (@RequestBody user infoUsuario){
        HashMap<String,Object> response=new HashMap<String,Object>();
        if ((this.ur.existsByEmail(infoUsuario.getEmail()) || (this.ur.existsByUsername(infoUsuario.getUsername()))))
        {
            response.put("user",null);
            response.put("message","el usuario o correo ya existe");
            response.put("successful",false);
        }
        else {
            if (infoUsuario.is_valid()){
                infoUsuario.setPassword(passwordEncoder.encode(infoUsuario.getPassword()));
                infoUsuario.setEnabled(Boolean.TRUE);
                infoUsuario.setRoles(this.rlr.findById("2").get());
                this.ur.save(infoUsuario);
                infoUsuario.setPassword("");
                response.put("user",infoUsuario);
                response.put("message","el usuario fue creado exitosamente");
                response.put("successful",true);
            }
            else {
                response.put("user",null);
                response.put("message","el usuario no pudo ser creado");
                response.put("successful",false);
            }
        }
        return response;
    }

    @PutMapping("/rol/{id}/us/{idus}")
    public HashMap addrol(Authentication auth, @PathVariable("id") String id, @PathVariable("idus") String idus){

        HashMap<String,Object> response=new HashMap<String,Object>();

        roles rol =this.rlr.findById(id).get();
        if (rol!=null){
            user us=this.ur.findById(idus).get();
            us.setRoles(rol);
            this.ur.save(us);
            response.put("user",us);
            response.put("message","el rol fuer añadido");
            response.put("successful",true);
        }
        else{
            response.put("user",null);
            response.put("message","el rol no pudo añadido");
            response.put("successful",true);
        }
        return response;
    }

    @Autowired
    private PasswordEncoder passwordEncoder;

}
