package com.example.security.controller;

import com.example.security.models.permission;
import com.example.security.models.roles;
import com.example.security.repositories.classRolRepo;
import com.example.security.repositories.rolesRepositorie;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.servlet.http.HttpServletRequest;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.security.core.Authentication;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.*;


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

    private static final ArrayList<String> urlIgnore = new ArrayList<>();
    static {
        urlIgnore.add("/create_user");
        urlIgnore.add("/login");
    }

    @GetMapping("/verificar_permisos")
    public ResponseEntity<Object> verificarPermisos(@RequestBody Map<String, String> data, HttpServletRequest request) {
        if (urlIgnore.contains(data.get("url"))){
            return ResponseEntity.ok(new JsonResponse(true));
        }
        else {
            String authHeader = request.getHeader("Authorization");
            if (authHeader == null) {
                // Usuario no autenticado
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new JsonResponse(false));
            }

            if (authHeader.startsWith("Bearer")) {
                // Autenticación con Token
                String token = authHeader.substring(7); // Ignora la palabra "Bearer " y extrae solo el token
                Authentication authentication  = authenticateWithToken( token);
                return comprobarPermisos(data, authentication, request);
            } else if (authHeader.startsWith("Basic")) {
                // Autenticación con Basic Auth
                String base64Credentials = authHeader.substring(6); // Ignora la palabra "Basic " y extrae solo las credenciales en formato Base64
                String credentials = new String(Base64.getDecoder().decode(base64Credentials));
                String[] splitCredentials = credentials.split(":", 2); // Separa las credenciales en usuario y contraseña
                String username = splitCredentials[0];
                String password = splitCredentials[1];
                System.out.println(username+"  "+password);
                Authentication authentication = authenticateWithBasicAuth(username, password);
                System.out.println(authentication);
                return comprobarPermisos(data, authentication, request);
            } else {
                // Autenticación no soportada
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new JsonResponse(false));
            }
        }

    }

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtDecoder jwtDecoder;
    private Authentication authenticateWithToken(String token) {
        Jwt jwt = jwtDecoder.decode(token);
        String username = jwt.getSubject();
        Collection<SimpleGrantedAuthority> authorities = getAuthoritiesFromScope(jwt.getClaimAsString("scope"));
        return new UsernamePasswordAuthenticationToken(username, null, authorities);
    }

    private Collection<SimpleGrantedAuthority> getAuthoritiesFromScope(String scope) {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        if (scope != null) {
            for (String authority : scope.split(" ")) {
                authorities.add(new SimpleGrantedAuthority(authority));
            }
        }
        return authorities;
    }




    private Authentication authenticateWithBasicAuth(String username, String password) {
        // Crea un objeto de tipo UsernamePasswordAuthenticationToken con el usuario y la contraseña como credenciales
        UsernamePasswordAuthenticationToken authCredentials = new UsernamePasswordAuthenticationToken(username, password);

        // Utiliza el AuthenticationManager de Spring Security para autenticar las credenciales
        try {
            Authentication authentication = authenticationManager.authenticate(authCredentials);
            return authentication;
        } catch (AuthenticationException e) {
            // Si la autenticación falla, retorna null
            return null;
        }
    }


    private ResponseEntity<Object> comprobarPermisos(@RequestBody Map<String, String> data, Authentication  authentication, HttpServletRequest request){

        if (authentication==null) {
            // Usuario no autenticado
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new JsonResponse(false));
        }

        String uri = data.get("url");
        String method = data.get("method");


        roles rol = getRol(authentication, request);

        if (rol == null) {
            // El usuario no tiene rol asignado
            System.out.println("no tiene rol");
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

    private roles getRol(@NotNull Authentication authentication, @NotNull HttpServletRequest request){
        RestTemplate restTemplate = new RestTemplate();
        String rolurl="http://127.0.0.1:9999/cnf/getRol/"+authentication.getAuthorities().toString().replace("[","").replace("]","");
        System.out.println("llama a "+authentication.getAuthorities().toString());
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
