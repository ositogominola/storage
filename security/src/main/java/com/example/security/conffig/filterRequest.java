package com.example.security.conffig;

import com.example.security.models.permission;
import com.example.security.models.roles;
import com.example.security.repositories.classRolRepo;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;

@Component
public class filterRequest extends GenericFilterBean {

    @Autowired
    private classRolRepo rolRepo;



    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        ArrayList<String> urlIgnore = new ArrayList<>();
        var response = (HttpServletResponse) servletResponse;
        var request = (HttpServletRequest) servletRequest;
        var autentication=SecurityContextHolder.getContext().getAuthentication();

        urlIgnore.add("/error");
        urlIgnore.add("/user/create");
        urlIgnore.add("/token");

        if (request.getRequestURI().contains("cnf/getRol/") & autentication!=null){
            filterChain.doFilter(servletRequest,servletResponse);
        }
        else
        {
            if (urlIgnore.contains(request.getRequestURI())){
                filterChain.doFilter(servletRequest,servletResponse);
            }
            else if (autentication!=null){
                RestTemplate restTemplate = new RestTemplate();
                if (autentication.getAuthorities().toString().startsWith("[SCOPE_")){
                    String rolurl="http://127.0.0.1:8080/cnf/getRol/"+autentication.getAuthorities().toString().replace("[SCOPE_","").replace("]","");

                    //creamos el request
                    HttpHeaders headers = new HttpHeaders();
                    headers.set("Authorization",request.getHeader("Authorization"));
                    HttpEntity<String> httpEntity = new HttpEntity<>("some body", headers);

                    //llamamos el rol
                    roles rol= restTemplate.exchange(rolurl, HttpMethod.GET, httpEntity, roles.class).getBody();

                    if (!(rol == null)){

                        //obtiene los permisos
                        Set<permission> permisos= rol.getPermissions();
                        boolean pr=false;
                        for (permission p: permisos) {
                            if (p.getUrl().equals(request.getRequestURI()) & p.getMethod().equals(request.getMethod())){
                                pr=true;
                                break;
                            }
                        }
                        if (pr){
                            filterChain.doFilter(servletRequest,servletResponse);
                        }
                        else {
                            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                        }
                    }
                }
                else {
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                }
            }
            else {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            }
        }

    }

}
