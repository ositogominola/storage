package com.example.security.controller;

import com.example.security.service.TokenService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
public class AuthController {

    private static final Logger LOG = LoggerFactory.getLogger(AuthController.class);

    private final TokenService tokenService;

    public AuthController(TokenService tokenService) {
        this.tokenService = tokenService;
    }


    @PostMapping("/login")
    public String token(Authentication authentication) {
        String token = tokenService.generateToken(authentication);
        return token;
    }

    @GetMapping("/isAuthenticated")
    public ResponseEntity<?> isAuthenticated(HttpServletRequest request) {
        String token = request.getCookies()[0].getValue();
        boolean isValid = tokenService.verifyToken(token);
        if (isValid) {
            return ResponseEntity.ok("El usuario está autenticado");
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("El usuario no está autenticado");
        }
    }

}