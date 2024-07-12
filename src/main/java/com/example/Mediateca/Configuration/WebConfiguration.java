package com.example.Mediateca.Configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Configuracion que implementa WebMvcConfigurer para definir politicas CORS
 * globales para la aplicacion.
 */
@Configuration
public class WebConfiguration implements WebMvcConfigurer {

    /**
     * Configura y proporciona un WebMvcConfigurer con las politicas CORS definidas.
     * Esta configuración de CORS es aplicable globalmente para todas las rutas en
     * la aplicacion.
     *
     * @return WebMvcConfigurer
     */
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("http://kidvertising.es")
                        .allowedMethods("POST", "GET", "PUT", "OPTIONS", "DELETE")
                        .allowedHeaders("*");
            }
        };
    }
}
