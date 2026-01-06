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

    private final JwtService jwtService; // Inyectamos el servicio

    public JwtAuthenticationFilter(JwtService jwtService) {
            this.jwtService = jwtService;
        }
    @Override
    protected void doFilterInternal(HttpServletRequest request, 
                                    HttpServletResponse response, 
                                    FilterChain filterChain) throws ServletException, IOException {

        String path = request.getRequestURI();
        
        // 1. EXCLUIR SOLO SWAGGER (Aquí quitamos "/api/")
        if (path.contains("/v3/api-docs") || 
            path.contains("/swagger-ui") || 
            path.equals("/swagger-ui.html")) {
            filterChain.doFilter(request, response);
            return;
        }

        // 2. OBTENER HEADER
        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            enviarErrorJson(response, "Falta el encabezado Authorization: Bearer", HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        // Extraemos el valor después de "Bearer "
        String tokenRecibido = authHeader.substring(7);

        // 3. VALIDACIÓN USANDO EL SERVICIO (Criptográfica y Dinámica)
        if (!jwtService.isTokenValid(tokenRecibido)) {
            enviarErrorJson(response, "Token inválido o expirado. Acceso denegado.", HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        // Si el token es idéntico al que definimos, dejamos pasar
        filterChain.doFilter(request, response);
    }

    private void enviarErrorJson(HttpServletResponse response, String mensaje, int status) throws IOException {
        response.setStatus(status);
        response.setContentType("application/json;charset=UTF-8");
        Map<String, Object> errorBody = new HashMap<>();
        errorBody.put("error", mensaje);
        errorBody.put("status", status);
        ObjectMapper mapper = new ObjectMapper();
        response.getWriter().write(mapper.writeValueAsString(errorBody));
    }
}
