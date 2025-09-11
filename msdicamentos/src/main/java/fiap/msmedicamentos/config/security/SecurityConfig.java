package fiap.msmedicamentos.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, UserDetailsService userDetailsService) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(authz -> authz
                // Swagger e documentação - público
                .requestMatchers("/", "/swagger-ui/**", "/v3/api-docs/**", "/swagger-resources/**", "/webjars/**").permitAll()
                
                // Consultas públicas (GET) - qualquer usuário autenticado pode fazer
                .requestMatchers(HttpMethod.GET, "/api/medicamentos/**").hasAnyRole("ADMIN", "USUARIO")
                .requestMatchers(HttpMethod.GET, "/api/unidades-saude/**").hasAnyRole("ADMIN", "USUARIO")
                .requestMatchers(HttpMethod.GET, "/api/usuarios/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET, "/api/estoque/**").hasAnyRole("ADMIN", "USUARIO")
                
                // Operações de CUD - apenas ADMIN
                .requestMatchers(HttpMethod.POST, "/api/medicamentos/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/api/medicamentos/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/medicamentos/**").hasRole("ADMIN")
                
                .requestMatchers(HttpMethod.POST, "/api/unidades-saude/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/api/unidades-saude/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/unidades-saude/**").hasRole("ADMIN")
                
                .requestMatchers(HttpMethod.POST, "/api/usuarios/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/api/usuarios/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/usuarios/**").hasRole("ADMIN")
                
                // Operações de estoque - apenas ADMIN
                .requestMatchers(HttpMethod.POST, "/api/estoque/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/api/estoque/**").hasRole("ADMIN")
                
                // Qualquer outra requisição precisa estar autenticada
                .anyRequest().authenticated()
            )
            .httpBasic(httpBasic -> {})
            .userDetailsService(userDetailsService);

        return http.build();
    }
}
