package com.example.security.controller;


import com.example.security.service.AutorizacionService;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;

import java.util.*;


@RestController
@RequestMapping("/cnf")
public class comprobacion {

    @Autowired
    private AutorizacionService AuthService;

    //obtener rols



    @GetMapping("/verificar_permisos")
    public ResponseEntity<Object> verificarPermisos(@RequestBody Map<String, Object> data, Authentication auth) {
        if (auth == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new JsonResponse(false));

        boolean comprobacion=(boolean) AuthService.comprobarPermisos(data, auth).get("tienePermiso");
        if (comprobacion) return ResponseEntity.ok(new JsonResponse(true));
        else return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new JsonResponse(false));
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
