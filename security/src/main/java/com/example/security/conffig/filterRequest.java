package com.example.security.conffig;

import com.example.security.controller.comprobacion;
import com.example.security.models.permission;
import com.example.security.models.roles;
import com.example.security.repositories.rolesRepositorie;
import com.example.security.service.AutorizacionService;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;
import java.util.Set;

@Component
public class filterRequest extends GenericFilterBean {

    private AutorizacionService AuthService;



    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        if (AuthService == null) {
            ServletContext servletContext = servletRequest.getServletContext();
            WebApplicationContext webApplicationContext = WebApplicationContextUtils.getWebApplicationContext(servletContext);
            AuthService = webApplicationContext.getBean(AutorizacionService.class);
        }

        var response = (HttpServletResponse) servletResponse;

        HashMap<String,Object> data=new HashMap<>();
        data.put("url",((HttpServletRequest) servletRequest).getRequestURI());
        data.put("method",((HttpServletRequest) servletRequest).getMethod());

        System.out.println("verificando: "+data.toString());

        Authentication autentication=SecurityContextHolder.getContext().getAuthentication();

        if (autentication==null) response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        boolean tienePermiso= (boolean) AuthService.comprobarPermisos(data, autentication).get("tienePermiso");

        if (tienePermiso) filterChain.doFilter(servletRequest,servletResponse);
        else response.setStatus(HttpServletResponse.SC_FORBIDDEN);

    }



}
