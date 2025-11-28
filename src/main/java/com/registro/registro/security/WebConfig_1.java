//JG
package com.registro.registro.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            // ---- CSRF Protection ----
            .csrf(csrf -> csrf
                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
            )
            // ---- CORS Config ----
            .cors(cors -> {}) // Se habilita y se gestiona desde WebConfig
            // ---- Security Headers ----
            .headers(headers -> headers
                .httpStrictTransportSecurity(hsts -> hsts
                    .includeSubDomains(true)
                    .maxAgeInSeconds(31536000)  // 1 año
                )
                .contentSecurityPolicy(csp -> csp
                    .policyDirectives("default-src 'self'")
                )
                .xssProtection(xss -> xss.block(true))
                .frameOptions(frame -> frame.deny())
            )
            // ---- Autorización ----
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/auth/**").permitAll()  // login/registro expuesto
                .anyRequest().authenticated()
            );
        return http.build();
    }
}
