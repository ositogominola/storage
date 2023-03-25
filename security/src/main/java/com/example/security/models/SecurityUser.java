package com.example.security.models;

import com.example.security.repositories.rolesRepositorie;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class SecurityUser implements UserDetails {

    private final user userown;
    private rolesRepositorie rols;

    public SecurityUser(user userinfo) {
        this.userown = userinfo;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        roles rols = userown.getRoles();
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(rols.getName()));

        return authorities;

    }

    @Override
    public String getPassword() {
        return userown.getPassword();
    }

    @Override
    public String getUsername() {
        return userown.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return userown.getEnabled();
    }
}
