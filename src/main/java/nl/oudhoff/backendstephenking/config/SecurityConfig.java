package nl.oudhoff.backendstephenking.config;

import nl.oudhoff.backendstephenking.security.JwtRequestFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig  {

    private final JwtRequestFilter jwtRequestFilter;
    private final UserDetailsService userDetailsService;

    public SecurityConfig(JwtRequestFilter jwtRequestFilter, UserDetailsService userDetailsService) {
        this.jwtRequestFilter = jwtRequestFilter;
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
//                        .requestMatchers("/**").permitAll()
                                .requestMatchers(HttpMethod.GET, "/**").permitAll()
                                .requestMatchers(HttpMethod.POST, "/admin").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.GET, "/users").permitAll()
                                .requestMatchers(HttpMethod.POST, "/users/register").permitAll()
                                .requestMatchers(HttpMethod.POST, "/users/login").permitAll()
                                .requestMatchers(HttpMethod.DELETE, "/users/delete/**").permitAll()

                                .requestMatchers(HttpMethod.POST, "/books").permitAll()
                                .requestMatchers(HttpMethod.PUT, "/books/**").permitAll()
                                .requestMatchers(HttpMethod.GET, "/books/**").permitAll()
                                .requestMatchers(HttpMethod.GET, "/books/title/**").permitAll()
                                .requestMatchers(HttpMethod.GET, "/books").permitAll()
                                .requestMatchers(HttpMethod.DELETE, "/books/**").permitAll()
                                .requestMatchers(HttpMethod.GET, "/books/{id}/bookcovers").permitAll()
                                .requestMatchers(HttpMethod.POST, "/books/{id}/bookcovers").permitAll()

                                .requestMatchers(HttpMethod.POST, "/reviews").permitAll()
                                .requestMatchers(HttpMethod.PUT, "/reviews/**").permitAll()
                                .requestMatchers(HttpMethod.GET, "/reviews/**").permitAll()
                                .requestMatchers(HttpMethod.GET, "/reviews").permitAll()
                                .requestMatchers(HttpMethod.DELETE, "/reviews/**").permitAll()
                                .requestMatchers(HttpMethod.PUT, "/reviews/{reviewId}/books/{bookId}").permitAll()
                                .requestMatchers(HttpMethod.PUT, "/reviews/{reviewId}/users/{username}").permitAll()
                                .requestMatchers(HttpMethod.PUT, "/reviews/{reviewId}/books/{bookId}/users/{username}").permitAll()

                                .requestMatchers(HttpMethod.POST, "/auth").permitAll()
                                .requestMatchers("/authenticated").authenticated()
                                .requestMatchers("/authenticated").permitAll()
                                .anyRequest().denyAll()

                )
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:5173"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(true);
        configuration.setMaxAge(3600L); //1 uur

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
