package com.example.tictactoe.security;

import com.example.tictactoe.domain.service.UserService;
import com.example.tictactoe.security.jwt.JwtProvider;
import com.example.tictactoe.security.service.AuthService;
import com.example.tictactoe.security.service.AuthServiceImpl;
import com.example.tictactoe.security.service.CookieService;
import com.example.tictactoe.security.service.CookieServiceImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
    @Value("#{'${my.permitted}'.split(',')}")
    private List<String> permitted;

    @Bean
    public JwtProvider jwtProvider(
            @Value("${jwt.secret}") String secret,
            @Value("${jwt.expiration-access}") long expirationAccess,
            @Value("${jwt.expiration-refresh}") long expirationRefresh) {
        return new JwtProvider(secret, expirationAccess, expirationRefresh);
    }

    @Bean
    public AuthService authService(UserService userService, JwtProvider jwtProvider) {
        return new AuthServiceImpl(userService, jwtProvider);
    }

    @Bean
    public CookieService cookieService(@Value("${my.cookieDuration}") int cookieDuration) {
        return new CookieServiceImpl(cookieDuration);
    }

    @Bean
    public PasswordEncoder passwordEncoder(@Value("${my.encoderStrength}") int strength) {
        return new BCryptPasswordEncoder(strength);
    }

    @Bean
    public AuthFilter authFilter(AuthService authService) {
        return new AuthFilter(authService, permitted);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, AuthFilter authFilter) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests
                        (auth -> auth
                                .requestMatchers(permitted.toArray(new String[0])).permitAll()
                                .anyRequest().authenticated()
                        )
                .addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
