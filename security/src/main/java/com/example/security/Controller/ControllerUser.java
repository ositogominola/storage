package com.example.security.Controller;

import com.example.security.Models.User;
import com.example.security.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/User")
public class ControllerUser {
    @Autowired
    private UserRepository RU;

    //llamar todos los usuarios
    @GetMapping
    public List<User> getUser(){
        return this.RU.findAll();
    }

    //llama un usuario por id
    @GetMapping("{id}")
    public User getUser(@PathVariable String id){
        User usuario=this.RU.findById(id).orElse(null);
        return usuario;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/CreateUser")
    public User create(@RequestBody User infoUsuario){
        infoUsuario.setPassword(convertirSHA256(infoUsuario.getPassword()));
        return this.RU.save(infoUsuario);
    }



    public String convertirSHA256(String password) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-256");
        }
        catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
        byte[] hash = md.digest(password.getBytes());
        StringBuffer sb = new StringBuffer();
        for(byte b : hash) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }

}
