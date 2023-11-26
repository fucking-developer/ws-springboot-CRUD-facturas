package com.jonhdevelop.springbootapifacturas.auth.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jonhdevelop.springbootapifacturas.auth.service.JWTService;
import com.jonhdevelop.springbootapifacturas.auth.service.JWTServiceImpl;
import com.jonhdevelop.springbootapifacturas.entity.Usuario;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private JWTService jwtService;

    private AuthenticationManager authManager;


    public JWTAuthenticationFilter(AuthenticationManager authManager, JWTService jwtService) {
        this.authManager = authManager;
        setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher("/api/login", "POST"));
        this.jwtService = jwtService;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        String username = obtainUsername(request);
        String password = obtainPassword(request);

        if(username != null && password != null) {
            logger.info("Username desde form-data: " + username);
            logger.info("Password desde form-data: " + password);
        } else {
            Usuario user;
            try {
                user = new ObjectMapper().readValue(request.getInputStream(),Usuario.class);
                username = user.getUsername();
                password = user.getPassword();
                logger.info("Username desde raw: " + username);
                logger.info("Password desde raw: " + password);
            } catch (IOException e) {
                logger.info("Error");
            }
        }
        username = username.trim();
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, password);
        return authManager.authenticate(authToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        String token = jwtService.createToken(authResult);
        response.addHeader(JWTServiceImpl.HEADER, JWTServiceImpl.PREFIJO + token);
        Map<String, Object> body = new HashMap<>();
        body.put("token", token);
        body.put("user", (User) authResult.getPrincipal());
        body.put("mensaje", String.format("Hola %s, has iniciado sesión con éxito!", authResult.getName()));
        response.getWriter().write(new ObjectMapper().writeValueAsString(body));
        response.setStatus(200);
        response.setContentType("application/json");
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        Map<String, Object> body = new HashMap<>();
        body.put("mensaje", "Error de autenticación: username o password incorrecto!");
        body.put("error", failed.getMessage());
        response.getWriter().write(new ObjectMapper().writeValueAsString(body));
        response.setStatus(401);
        response.setContentType("application/json");
    }

}
