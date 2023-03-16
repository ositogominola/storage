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
import org.springframework.security.core.Authentication;
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

    private static final ArrayList<String> urlIgnore = new ArrayList<>();
    static {
        urlIgnore.add("/error");
        urlIgnore.add("/user/create");
        urlIgnore.add("/login");
        urlIgnore.add("/cnf/verificar_permisos");
    }


    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        var response = (HttpServletResponse) servletResponse;
        var request = (HttpServletRequest) servletRequest;
        Authentication autentication=SecurityContextHolder.getContext().getAuthentication();



        if (request.getRequestURI().contains("cnf/getRol/") & autentication!=null){filterChain.doFilter(servletRequest,servletResponse);}
        else
        {
            if (urlIgnore.contains(request.getRequestURI())){filterChain.doFilter(servletRequest,servletResponse);}
            else if (autentication!=null){

                if (autentication.getAuthorities().toString().startsWith("[SCOPE_")){
                    roles rol = getRol(autentication,request);
                    if (!(rol == null)){

                        //obtiene los permisos
                        Set<permission> permisos= rol.getPermissions();

                        for (permission p: permisos) {
                            if ( p.getUrl().equals(request.getRequestURI()) & p.getMethod().equals(request.getMethod())){
                                filterChain.doFilter(servletRequest,servletResponse);
                            }
                            else if (p.getUrl().endsWith("/**") & request.getRequestURI().startsWith(p.getUrl().replace("**","")))
                            {
                                filterChain.doFilter(servletRequest,servletResponse);
                            }
                        }
                    }
                }
            }
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }
    }

    private roles getRol(Authentication autentication, HttpServletRequest request){
        RestTemplate restTemplate = new RestTemplate();
        String rolurl="http://127.0.0.1:9999/cnf/getRol/"+autentication.getAuthorities().toString().replace("[SCOPE_","").replace("]","");

        //creamos el request
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization",request.getHeader("Authorization"));
        HttpEntity<String> httpEntity = new HttpEntity<>("some body", headers);

        //llamamos el rol
        return restTemplate.exchange(rolurl, HttpMethod.GET, httpEntity, roles.class).getBody();
    }

}
