package com.registro.registro.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, 
                                    HttpServletResponse response, 
                                    FilterChain filterChain) throws ServletException, IOException {

        // 1. Obtener el header Authorization
        String authHeader = request.getHeader("Authorization");

        // 2. Excluir rutas de Swagger y OpenAPI de la validación del token
        String path = request.getRequestURI();
        if (path.contains("/v3/api-docs") || 
            path.contains("/swagger-ui") || 
            path.contains("/swagger-resources") || 
            path.equals("/swagger-ui.html")) {

            filterChain.doFilter(request, response);
            return; // Dejamos pasar sin revisar el token
        }

        // 3. Validación del Token para el resto de rutas (Tu lógica actual)
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            enviarErrorJson(response, "Token de seguridad faltante o inválido", HttpServletResponse.SC_UNAUTHORIZED);
            return; 
        }
    }

  private void enviarErrorJson(HttpServletResponse response, String mensaje, int status) throws IOException {
    response.setStatus(status);
    // Agregamos el charset UTF-8 para que se vean bien las tildes
    response.setContentType("application/json;charset=UTF-8");

    Map<String, Object> errorBody = new HashMap<>();
    errorBody.put("error", mensaje);
    errorBody.put("status", status);

    ObjectMapper mapper = new ObjectMapper();
    response.getWriter().write(mapper.writeValueAsString(errorBody));
}
}
