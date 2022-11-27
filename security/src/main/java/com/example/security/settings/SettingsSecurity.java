package com.example.security.settings;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
public class SettingsSecurity{
    @Bean
    SecurityFilterChain SecurityWeb (HttpSecurity http) throws Exception{

        http.authorizeHttpRequests((authorize)->authorize
                .mvcMatchers("/User/**","/prueba").permitAll()
                .anyRequest().denyAll()
        ).formLogin(form -> form
                .loginPage("/login")
                .permitAll()
        ).cors().and().csrf().disable();
        return http.build();
    }

}
