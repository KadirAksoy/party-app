package com.kadiraksoy.partyapp.config;

import com.kadiraksoy.partyapp.security.JwtAuthenticationFilter;
import com.kadiraksoy.partyapp.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;



import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity// metot tabanlı güvenlik konfigürasyonunu etkinleştirir.
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;


    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userService.userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder);
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable
                )

                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authorizeHttpRequests(authorize -> authorize
                        //AuthController
                        .requestMatchers(HttpMethod.POST, "/api/users/signup", "/api/users/signing").permitAll()
                        .requestMatchers(HttpMethod.PUT,"/api/users/regenerate-otp", "/api/users/verify-account").permitAll()
                        //UserController
                        .requestMatchers(HttpMethod.GET,"/api/users/super-admin/**").hasAuthority("ROLE_SUPERADMIN")
                        .requestMatchers(HttpMethod.POST,"/api/users/super-admin/**").hasAuthority("ROLE_SUPERADMIN")
                        .requestMatchers(HttpMethod.DELETE,"/api/users/super-admin/**").hasAuthority("ROLE_SUPERADMIN")
                        .requestMatchers(HttpMethod.PUT,"/api/users/super-admin/**").hasAuthority("ROLE_SUPERADMIN")
                        //TestController
                        .requestMatchers(HttpMethod.GET, "/api/v1/test/anon").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/v1/test/users").hasAnyAuthority("ROLE_USER","ROLE_ADMIN","ROLE_SUPERADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/v1/test/admins").hasAnyAuthority("ROLE_ADMIN","ROLE_SUPERADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/v1/test/superadmin").hasAuthority("ROLE_SUPERADMIN")
                        //ImageController
                        .requestMatchers(HttpMethod.GET,"/api/images/**").hasAnyAuthority("ROLE_ADMIN","ROLE_SUPERADMIN")
                        .requestMatchers(HttpMethod.POST,"/api/images/**").hasAnyAuthority("ROLE_ADMIN","ROLE_SUPERADMIN")
                        .requestMatchers(HttpMethod.DELETE,"/api/images/**").hasAnyAuthority("ROLE_ADMIN","ROLE_SUPERADMIN")
                        .requestMatchers(HttpMethod.PUT,"/api/images/**").hasAnyAuthority("ROLE_ADMIN","ROLE_SUPERADMIN")
                        //PartyController
                        .requestMatchers(HttpMethod.GET,"/api/parties/**").permitAll()
                        .requestMatchers(HttpMethod.POST,"/api/parties/{partyId}/leave","/api/parties/{partyId}/attend").permitAll()
                        .requestMatchers(HttpMethod.POST,"/api/parties/**").hasAnyAuthority("ROLE_ADMIN","ROLE_SUPERADMIN")
                        .requestMatchers(HttpMethod.DELETE,"/api/parties/**").hasAnyAuthority("ROLE_ADMIN","ROLE_SUPERADMIN")
                        .requestMatchers(HttpMethod.PUT,"/api/parties/**").hasAnyAuthority("ROLE_ADMIN","ROLE_SUPERADMIN")
                        //RequestController
                        .requestMatchers(HttpMethod.GET,"/api/requests/**").hasAuthority("ROLE_SUPERADMIN")
                        .requestMatchers(HttpMethod.POST,"/api/requests/send").permitAll()
                        .requestMatchers(HttpMethod.POST,"/api/requests/accept/{email}").hasAuthority("ROLE_SUPERADMIN")
                        .requestMatchers(HttpMethod.DELETE,"/api/requests/**").hasAuthority("ROLE_SUPERADMIN")
                        .requestMatchers(HttpMethod.PUT,"/api/requests/**").permitAll()
                        //CommentController
                        .requestMatchers(HttpMethod.GET,"/api/comments/**").permitAll()
                        .requestMatchers(HttpMethod.POST,"/api/comments/**").permitAll()
                        .requestMatchers(HttpMethod.DELETE,"/api/requests/**").hasAnyAuthority("ROLE_ADMIN","ROLE_SUPERADMIN")
                        .requestMatchers(HttpMethod.PUT,"/api/requests/**").permitAll()
                        //Swagger
                        .requestMatchers(
                                        "/auth/**",
                                        "/v2/api-docs",
                                        "/v3/api-docs",
                                        "/v3/api-docs/**",
                                        "/swagger-resources",
                                        "/swagger-resources/**",
                                        "/configuration/ui",
                                        "/configuration/security",
                                        "/swagger-ui/**",
                                        "/webjars/**",
                                        "/swagger-ui.html"
                                )
                                .permitAll()
                )
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}