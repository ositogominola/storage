package com.example.security.controller;

import com.example.security.models.permission;
import com.example.security.models.roles;
import com.example.security.repositories.classRolRepo;
import com.example.security.repositories.rolesRepositorie;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.security.core.Authentication;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;


@RestController
@RequestMapping("/cnf")
public class comprobacion {

    @Autowired
    private classRolRepo rlp;

    //obtener rols
    @GetMapping("/getRol/{name}")
    public roles getrol(@PathVariable(value = "name") String name){
        return rlp.findroltByname(name);
    }



    @GetMapping("/verificar_permisos")
    public ResponseEntity<Object> verificarPermisos(@RequestBody Map<String, String> data, Authentication authentication, HttpServletRequest request) {
        System.out.println("verificando permiso");
        if (authentication == null) {
            // Usuario no autenticado
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new JsonResponse(false));
        }

        String uri = data.get("url");
        String method = data.get("method");


        roles rol = getRol(authentication, request);

        if (rol == null) {
            // El usuario no tiene rol asignado
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new JsonResponse(false));
        }

        Set<permission> permisos = rol.getPermissions();

        for (permission p : permisos) {
            if (p.getUrl().equals(uri) && p.getMethod().equals(method)) {
                // El usuario tiene los permisos necesarios
                return ResponseEntity.ok(new JsonResponse(true));
            } else if (p.getUrl().endsWith("/**") && uri.startsWith(p.getUrl().replace("**", ""))) {
                // El usuario tiene los permisos necesarios para un endpoint que utiliza un patrón comodín
                return ResponseEntity.ok(new JsonResponse(true));
            }
        }
        // El usuario no tiene los permisos necesarios
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new JsonResponse(false));
    }


    private roles getRol(Authentication authentication, HttpServletRequest request){
        RestTemplate restTemplate = new RestTemplate();
        String rolurl="http://127.0.0.1:9999/cnf/getRol/"+authentication.getAuthorities().toString().replace("[SCOPE_","").replace("]","");

        //creamos el request
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization",request.getHeader("Authorization"));
        HttpEntity<String> httpEntity = new HttpEntity<>("some body", headers);

        //llamamos el rol
        return restTemplate.exchange(rolurl, HttpMethod.GET, httpEntity, roles.class).getBody();
    }

    public class JsonResponse {
        @JsonProperty("authorized")
        private boolean authorized;

        public JsonResponse(boolean message) {
            this.authorized = message;
        }

        public boolean getMessage() {
            return authorized;
        }
    }

}
