package it.unical.inf.gruppoea.vinteddu.security.config;


import it.unical.inf.gruppoea.vinteddu.security.RequestFilter;
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

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfiguration {
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }

    private final RequestFilter requestFilter;

    public SecurityConfiguration(RequestFilter requestFilter) {
        this.requestFilter = requestFilter;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http.
                authorizeHttpRequests().requestMatchers("/api/v1/register", "/api/v1/authenticate", "/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll().and().
                authorizeHttpRequests().anyRequest().authenticated().and().csrf().disable().
                sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().
                addFilterBefore(requestFilter, UsernamePasswordAuthenticationFilter.class).build();
    }
}
