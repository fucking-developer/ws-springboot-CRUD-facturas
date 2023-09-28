package com.jonhdevelop.springbootapifacturas.auth.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss z");

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        // Personaliza respuesta de error 403 aquí
        String formattedDate = dateFormat.format(new Date());
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", formattedDate);
        body.put("mensaje", "Acceso denegado!");
        body.put("error", accessDeniedException.getMessage());
        body.put("path", request.getRequestURI());
        response.getWriter().write(new ObjectMapper().writeValueAsString(body));
        //response.setStatus(403);
        response.setContentType("application/json");
    }
}
