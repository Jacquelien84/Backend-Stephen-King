package nl.oudhoff.backendstephenking.security;

import nl.oudhoff.backendstephenking.filter.JwtRequestFilter;
import nl.oudhoff.backendstephenking.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;



@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final UserService userDetailsService;

    private final JwtRequestFilter jwtRequestFilter;


    public SecurityConfig(UserService userDetailsService, JwtRequestFilter jwtRequestFilter) {
        this.userDetailsService = userDetailsService;
        this.jwtRequestFilter = jwtRequestFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
//                        .requestMatchers("/**").permitAll()
                                .requestMatchers(HttpMethod.GET, "/**").permitAll()
                                .requestMatchers(HttpMethod.POST, "/login").permitAll()
                                .requestMatchers(HttpMethod.POST, "/register").permitAll()
                                .requestMatchers(HttpMethod.GET, "/users").permitAll()
                                .requestMatchers(HttpMethod.GET, "/users/**").permitAll()
                                .requestMatchers(HttpMethod.POST, "/users/**").authenticated()
                                .requestMatchers(HttpMethod.PUT, "/users/**").permitAll()
                                .requestMatchers(HttpMethod.DELETE, "/users/**").authenticated()

                                .requestMatchers(HttpMethod.POST, "/books").hasAuthority("ADMIN")
                                .requestMatchers(HttpMethod.PUT, "/books/**").hasAuthority("ADMIN")
                                .requestMatchers(HttpMethod.GET, "/books/**").permitAll()
                                .requestMatchers(HttpMethod.GET, "/books/title/**").permitAll()
                                .requestMatchers(HttpMethod.GET, "/books").permitAll()
                                .requestMatchers(HttpMethod.DELETE, "/books/**").hasAuthority("ADMIN")
                                .requestMatchers(HttpMethod.GET, "/books/{id}/bookcovers").permitAll()
                                .requestMatchers(HttpMethod.POST, "/books/{id}/bookcovers").hasAuthority("ADMIN")

                                .requestMatchers("/**").authenticated()
                                .anyRequest().denyAll()
                )
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .csrf(csrf -> csrf.disable());
        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(PasswordEncoder passwordEncoder) {
        var auth = new DaoAuthenticationProvider();
        auth.setPasswordEncoder(passwordEncoder);
        auth.setUserDetailsService(userDetailsService);
        return new ProviderManager(auth);
    }
}