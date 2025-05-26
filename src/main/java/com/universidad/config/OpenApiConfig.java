package com.universidad.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.context.annotation.Configuration;

/**
 * Configuración de OpenAPI para la API de la Universidad.
 * Esta clase define la documentación de la API y el esquema de seguridad para el uso de JWT Bearer.
 */
@Configuration
@OpenAPIDefinition( //  Definición de OpenAPI
    info = @Info(title = "API Universidad", version = "1.0"), // Información general de la API
    security = @SecurityRequirement(name = "bearerAuth") // Requerimiento de seguridad para usar JWT Bearer
)
@SecurityScheme(
    name = "bearerAuth", // Nombre del esquema de seguridad
    description = "Usar el token JWT Bearer para autenticar las solicitudes.", // Descripción del esquema de seguridad
    type = SecuritySchemeType.HTTP, // Tipo de esquema de seguridad
    scheme = "bearer",  // Esquema de seguridad HTTP
    bearerFormat = "JWT" // Formato del token Bearer
)
public class OpenApiConfig {
    // Configuración de seguridad para Swagger/OpenAPI con JWT Bearer
}
