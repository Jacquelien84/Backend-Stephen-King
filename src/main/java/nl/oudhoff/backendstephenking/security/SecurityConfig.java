package nl.oudhoff.backendstephenking.security;

import nl.oudhoff.backendstephenking.filter.JwtRequestFilter;
import nl.oudhoff.backendstephenking.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
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

    @Bean
    protected SecurityFilterChain filter(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .httpBasic(basic -> basic.disable())
                .cors(Customizer.withDefaults())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.GET, "/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/login").permitAll()
                        .requestMatchers(HttpMethod.POST, "/register").permitAll()

                        .requestMatchers(HttpMethod.DELETE, "/users/**").authenticated()

                        .requestMatchers(HttpMethod.GET, "/books/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/books/title/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/books").permitAll()
                        .requestMatchers(HttpMethod.GET, "/books/{id}/bookcovers").permitAll()
                        .requestMatchers(HttpMethod.POST, "/books/{id}/bookcovers").permitAll()

                        .requestMatchers(HttpMethod.POST, "/books").permitAll()
                        .requestMatchers(HttpMethod.PUT, "/books/**").permitAll()
                        .requestMatchers(HttpMethod.DELETE, "/books/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/books/**").permitAll()

                        .requestMatchers(HttpMethod.POST, "/auth").permitAll()
                        .requestMatchers("/authenticated").authenticated()

                        .anyRequest().denyAll()
                )
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
