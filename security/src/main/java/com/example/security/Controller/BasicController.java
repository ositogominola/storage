package com.example.security.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Controller
@RequestMapping("")
public class BasicController {
    @GetMapping("/login")
    public String login() {
        return "loginSgt";
    }
    @GetMapping("/prueba")
    public String prueba(){
        return "loginSgt";
    }
}
