package fiap.msmedicamentos.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
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
        // Usando NoOpPasswordEncoder para senhas em texto plano
        return org.springframework.security.crypto.password.NoOpPasswordEncoder.getInstance();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, UserDetailsService userDetailsService) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(authz -> authz
                // Swagger público
                .requestMatchers("/", "/swagger-ui/**", "/v3/api-docs/**").permitAll()
                // Criação de usuário público
                .requestMatchers(HttpMethod.POST, "/api/usuarios").permitAll()
                // Consultas (GET) de medicamentos, unidades e estoque públicas
                .requestMatchers(HttpMethod.GET, "/api/medicamentos/**", "/api/unidades-saude/**", "/api/estoque/**").permitAll()
                // GET de usuários continua protegido
                .requestMatchers(HttpMethod.GET, "/api/usuarios/**").hasAnyRole("ADMIN", "USUARIO")
                // POST/PUT/DELETE - só admin (exceto criação de usuário que já foi permitida)
                .requestMatchers(HttpMethod.POST, "/api/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/api/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/**").hasRole("ADMIN")
                .anyRequest().authenticated()
            )
            .httpBasic(Customizer.withDefaults())
            .userDetailsService(userDetailsService);

        return http.build();
    }
}
