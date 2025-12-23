//package com.registro.registro.security; // Asegúrate de que este paquete sea correcto
//
//import io.swagger.v3.oas.models.Components;
//import io.swagger.v3.oas.models.OpenAPI;
//import io.swagger.v3.oas.models.security.SecurityRequirement;
//import io.swagger.v3.oas.models.security.SecurityScheme;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class OpenApiConfig {
//
//    @Bean
//    public OpenAPI customOpenAPI() {
//        return new OpenAPI()
//            .addSecurityItem(new SecurityRequirement().addList("BearerAuth"))
//            .components(new Components()
//                .addSecuritySchemes("BearerAuth", new SecurityScheme()
//                    .name("BearerAuth")
//                    .type(SecurityScheme.Type.HTTP)
//                    .scheme("bearer")
//                    .bearerFormat("JWT")));
//    }
//}