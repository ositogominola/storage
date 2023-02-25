package com.example.security.controller;

import com.example.security.models.factory;
import com.example.security.models.user;
import com.example.security.repositories.factoryRepository;
import com.example.security.repositories.userRepositorie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.Set;
import java.sql.*;
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
    public ResponseEntity<String> deleteFactory(@PathVariable(value = "id") int id, Authentication auth) {
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

    @PatchMapping("/update/{id}")
    public ResponseEntity<String> UpdateFactory(@PathVariable(value = "id") int id, Authentication auth) {
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

    @Value("${spring.datasource.url}")
    private String url;
    @Value("${spring.datasource.username}")
    private String user;
    @Value("${spring.datasource.password}")
    private String password;
    @GetMapping("/getByid/{id}")
    public ResponseEntity<factory> getbyid(@PathVariable Integer id, Authentication auth){
        factory ownerFactory = null;
        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM factory as fc inner join user as us on us.id_user=fc.user_id and fc.id=? and us.username=?")) {
            stmt.setInt(1, id);
            stmt.setString(2, auth.getName());
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                ownerFactory = new factory();
                ownerFactory.setId(rs.getInt("id"));
                ownerFactory.setNombre(rs.getString("nombre"));
                ownerFactory.setUsuario(this.rpuser.findByUsername(auth.getName()).get());
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        if (ownerFactory == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(ownerFactory);
        }
    }

}
