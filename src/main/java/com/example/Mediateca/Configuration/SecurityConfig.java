package com.example.Mediateca.Configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Configuracion de seguridad para la aplicacion.
 * Se definen las politicas de acceso y autenticacion.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

        /**
         * Configura el filtro de seguridad HTTP.
         * 
         * @param http la instancia HttpSecurity para configurar la seguridad web.
         * @return la cadena de filtros de seguridad configurada.
         * @throws Exception si ocurre un error al configurar la seguridad.
         */
        @Bean
        public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
                http
                                .csrf(csrf -> csrf
                                                .ignoringRequestMatchers("/notificaciones/crearNotificacion/**")
                                                .ignoringRequestMatchers("/actualizarRol/**")
                                                .ignoringRequestMatchers("/eliminarAnuncio/**")
                                                .ignoringRequestMatchers("/eliminarAnuncios/**")
                                                .ignoringRequestMatchers("/eliminarCategorias/**"))

                                .authorizeHttpRequests(authorize -> authorize
                                                .requestMatchers("/css/**", "/js/**", "/images/**", "/resources/**")
                                                .permitAll() // Permite recursos estaticos
                                                .requestMatchers("/", "/anuncios",
                                                                "/metricas",
                                                                "/inicio",
                                                                "/detalleAnuncio",
                                                                "/cargarUsuarios", "/login", "/files/**",
                                                                "/descargarAnuncio")
                                                .permitAll() // Permite acceso a estas rutas sin autenticacion
                                                .requestMatchers("/login", "/nuevaPassword", "/reset-password",
                                                                "/crearPassword", "/registro", "/resetPassword",
                                                                "/cambiarPassword")
                                                .permitAll() // Permite el acceso a las paginas relacionadas con el
                                                             // login
                                                .requestMatchers("/gestionarCategorias", "/eliminarCategorias",
                                                                "/eliminarAnuncio", "/eliminarAnuncios",
                                                                "/cargarUsuarios",
                                                                "/aprobarNotificacion", "/rechazarNotificacion",
                                                                "/rechazarNotificacion",
                                                                "/gestionarUsuarios", "/agregarUsuario",
                                                                "/actualizarRol",
                                                                "/eliminarUsuarios")
                                                .hasRole("PROFESOR") // Solo accesibles para el rol de PROFESOR
                                                .anyRequest().authenticated()) // Cualquier otra solicitud requiere
                                                                               // autenticacion
                                .formLogin(form -> form
                                                .loginPage("/login") // Especifica la pagina de login
                                                .defaultSuccessUrl("/inicio", true)
                                                .permitAll())
                                .logout(logout -> logout
                                                .logoutSuccessUrl("/inicio")
                                                .invalidateHttpSession(true)
                                                .deleteCookies("JSESSIONID")
                                                .permitAll())
                                .exceptionHandling(exception -> exception
                                                .accessDeniedPage("/inicio"));
                return http.build();
        }

        /**
         * Configura el codificador de contraseñas.
         * 
         * @return una instancia de BCryptPasswordEncoder.
         */
        @Bean
        public PasswordEncoder passwordEncoder() {
                return new BCryptPasswordEncoder();
        }
}