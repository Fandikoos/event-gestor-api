package com.svalero.event_gestor.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ApplicationConfig implements WebMvcConfigurer {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        // Ignorar ambigüedad en el mapeo para el error de multiples hierarchies
        modelMapper.getConfiguration().setAmbiguityIgnored(true);
        return modelMapper;
    }

    //Solucion a problema de CORS para todas las rutas para el frontend de Angular
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:4200")  // Permitimos el frontend de Angular
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")  // Métodos permitidos
                .allowedHeaders("*")  // Permite todos los encabezados
                .allowCredentials(true);  // Autenticacion de cookies
    }
}
