package br.uscs.gestao_agenda_backend.infrastructure.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Autowired
    private SecurityFilter securityFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize -> authorize
                        .antMatchers(HttpMethod.POST, "/auth/login").permitAll()
                        .antMatchers(HttpMethod.POST, "/sala/**").permitAll()
                        .antMatchers(HttpMethod.GET, "/estagiario/**").permitAll()
                        .antMatchers(HttpMethod.POST, "/estagiario/**").permitAll()
                        .antMatchers(HttpMethod.PUT, "/estagiario/**").permitAll()
                        .antMatchers(HttpMethod.DELETE, "/estagiario/**").permitAll()
//                        .antMatchers(HttpMethod.GET, "/paciente/listar").hasRole("ADMINS")
                        .antMatchers(HttpMethod.GET, "/paciente/**").permitAll()
                        .antMatchers(HttpMethod.POST, "/paciente/**").permitAll()
                        .antMatchers(HttpMethod.PUT, "/paciente/**").permitAll()
                        .antMatchers(HttpMethod.DELETE, "/paciente/**").permitAll()
                        .anyRequest().authenticated()
                )
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    // TODO: Remover depois dos testes
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().antMatchers("/h2-console/**")
                .antMatchers("/swagger-ui/**")
                .antMatchers("/v3/api-docs/**")
                .antMatchers("/configuration/**")
                .antMatchers("/v3/api-docs/**");
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
