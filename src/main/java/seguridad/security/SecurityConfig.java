package seguridad.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity

public class SecurityConfig {
	
	private final JwtAuthenticationFilter jwt;
	
	//Activar encriptación de password
	@Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
	
	@Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
	
	@Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth -> auth
                // Auth + Swagger públicos
                .requestMatchers("/auth/**").permitAll()
                .requestMatchers(
                    "/v3/api-docs/**",
                    "/swagger-ui/**",
                    "/swagger-ui.html"
                ).permitAll()

                // TODO: luego pondremos reglas por rol para mercancias/camiones/etc.
             // MERCANCIAS:
                // Crear/editar/borrar -> SOLO EMPRESA
                .requestMatchers(org.springframework.http.HttpMethod.POST, "/mercancias/**").hasRole("EMPRESA")
                .requestMatchers(org.springframework.http.HttpMethod.PUT, "/mercancias/**").hasRole("EMPRESA")
                .requestMatchers(org.springframework.http.HttpMethod.DELETE, "/mercancias/**").hasRole("EMPRESA")

                // Ver pendientes / buscar -> CONDUCTOR y EMPRESA (los dos)
                .requestMatchers(org.springframework.http.HttpMethod.GET, "/mercancias/pendientes")
                .hasAnyRole("EMPRESA","CONDUCTOR")
                
                .requestMatchers(org.springframework.http.HttpMethod.GET, "/mercancias/buscar")
                .hasAnyRole("EMPRESA","CONDUCTOR")
                
                .requestMatchers(org.springframework.http.HttpMethod.GET, "/mercancias/conductor/**")
                .hasAnyRole("EMPRESA","CONDUCTOR")


                // Listar/ver por id -> autenticado (ya vale)
                .requestMatchers(org.springframework.http.HttpMethod.GET, "/mercancias/**").authenticated()
                
              // CAMIONES:
                // CRUD -> SOLO CONDUCTOR
                .requestMatchers(org.springframework.http.HttpMethod.POST, "/camiones/**").hasRole("CONDUCTOR")
                .requestMatchers(org.springframework.http.HttpMethod.PUT, "/camiones/**").hasRole("CONDUCTOR")
                .requestMatchers(org.springframework.http.HttpMethod.DELETE, "/camiones/**").hasRole("CONDUCTOR")

               // conductor consulta los suyos
                .requestMatchers(org.springframework.http.HttpMethod.GET, "/camiones/mios/**").hasRole("CONDUCTOR")

                // ENUNCIADO: EMPRESA consulta disponibles de un conductor
                .requestMatchers(org.springframework.http.HttpMethod.GET, "/camiones/conductor/**").hasRole("EMPRESA")

                // ver por id /camiones/{id} -> autenticado
                .requestMatchers(org.springframework.http.HttpMethod.GET, "/camiones/**").authenticated()
                
             // INSCRIPCIONES:
                .requestMatchers(org.springframework.http.HttpMethod.POST, "/inscripciones/**").hasRole("CONDUCTOR")
                .requestMatchers(org.springframework.http.HttpMethod.PUT, "/inscripciones/*/aceptar").hasRole("EMPRESA")
                .requestMatchers(org.springframework.http.HttpMethod.PUT, "/inscripciones/*/rechazar").hasRole("EMPRESA")

                .requestMatchers(org.springframework.http.HttpMethod.GET, "/inscripciones/mercancia/**").hasAnyRole("EMPRESA","CONDUCTOR")

                // CRUD general (si quieres):
                .requestMatchers(org.springframework.http.HttpMethod.GET, "/inscripciones/**").authenticated()
                .requestMatchers(org.springframework.http.HttpMethod.DELETE, "/inscripciones/**").hasRole("CONDUCTOR")

             // INCIDENCIAS:
                // Crear/editar -> CONDUCTOR
                .requestMatchers(org.springframework.http.HttpMethod.POST, "/incidencias/**").hasRole("CONDUCTOR")
                .requestMatchers(org.springframework.http.HttpMethod.PUT, "/incidencias/**").hasRole("CONDUCTOR")

                // Borrar -> lo puedes dejar EMPRESA o solo CONDUCTOR. Para “no cero”, pon EMPRESA (admin)
                .requestMatchers(org.springframework.http.HttpMethod.DELETE, "/incidencias/**").hasRole("EMPRESA")

                // Consultas -> autenticado (o ambos roles)
                .requestMatchers(org.springframework.http.HttpMethod.GET, "/incidencias/**").hasAnyRole("EMPRESA","CONDUCTOR")
                .anyRequest().authenticated()
            )
            .addFilterBefore(jwt, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }


}
