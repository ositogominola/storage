package com.example.security.service;

import com.example.security.controller.comprobacion;
import com.example.security.models.permission;
import com.example.security.models.roles;
import com.example.security.repositories.rolesRepositorie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Service
public class AutorizacionService {

    @Autowired
    private rolesRepositorie rlp;
    private static final Logger logger = LoggerFactory.getLogger(AutorizacionService.class);

    private static final ArrayList<String> urlIgnore = new ArrayList<>();
    static {
        urlIgnore.add("/create_user");
        urlIgnore.add("/login");
        urlIgnore.add("/error");
        urlIgnore.add("/user/create");
        urlIgnore.add("/login");
        urlIgnore.add("/cnf/verificar_permisos");
        urlIgnore.add("/isAuthenticated");
    }

    public HashMap<String, Object> comprobarPermisos(Map<String, Object> data, Authentication authentication){

        HashMap<String, Object> resonse=new HashMap<>();
        resonse.put("tienePermiso",false);
        String uri =(String) data.get("url");
        String method =(String) data.get("method");

        logger.info("Comprobando permisos: path "+uri+ " methodo "+method);

        if (urlIgnore.contains(uri)){
            logger.info("permiso comprobado");
            resonse.put("tienePermiso",true);
            return resonse;
        }

        if (authentication.getAuthorities() != null){
            Optional<roles>  rol = getRol(authentication);
            if (rol.isPresent()){
                //obtiene los permisos
                Set<permission> permisos= rol.get().getPermissions();

                for (permission p: permisos) {
                    if ( (p.getUrl().equals(uri) & p.getMethod().equals(method)) ||
                            (p.getUrl().endsWith("/**") & uri.startsWith(p.getUrl().replace("**","")) &
                                    p.getMethod().equals(method))){
                        logger.info("permiso comprobado");
                        resonse.put("tienePermiso",true);
                    }
                }
            }
        }

        return resonse;
    }

    private Optional<roles> getRol(Authentication autentication){
        GrantedAuthority authority = (GrantedAuthority) autentication.getAuthorities().toArray()[0];
        String roleName = authority.getAuthority().replace("SCOPE_","");
        return this.rlp.findByname(roleName);
    }
}
