package com.ondot.ondot_back.global.config;

import com.ondot.ondot_back.domain.organization.enums.OrganizationType;
import com.ondot.ondot_back.domain.organization.repository.OrganizationJpaRepository;
import com.ondot.ondot_back.domain.organization.service.AuthService;
import com.ondot.ondot_back.global.config.jwt.JwtTokenProvider;
import com.ondot.ondot_back.global.config.jwt.handler.CustomAccessDeniedHandler;
import com.ondot.ondot_back.global.config.jwt.handler.CustomAuthenticationEntryPoint;
import com.ondot.ondot_back.global.config.jwt.handler.OAuth2SuccessHandler;
import com.ondot.ondot_back.global.config.oauth.PrincipalOAuth2DetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final PrincipalOAuth2DetailsService principalOAuth2DetailsService;
    private AuthService authService;
    private final JwtTokenProvider jwtTokenProvider;
    private final CorsConfig corsConfig;
    private final OrganizationJpaRepository organizationRepository;

//    private final AuthenticationManager authenticationManager;


//    @Bean
//    public AuthenticationManager authenticationManager(
//            AuthenticationConfiguration authenticationConfiguration
//    ) throws Exception {
//        return authenticationConfiguration.getAuthenticationManager();
//    }
    @Bean
    public OrganizationType organizationType() {
        return OrganizationType.STUDENT_COUNCIL;
    }

    @Bean
    AuthenticationManager authenticationManager(
            AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf((csrfConfig) -> csrfConfig.disable())
                .addFilter(corsConfig.corsFilter())
//                .addFilter(new JwtAuthenticationFilter(authenticationManager, jwtTokenProvider))
//                .addFilter(new JwtAuthorizationFilter(authenticationManager, organizationRepository))
                .authorizeHttpRequests((authorizeRequests) ->
                                authorizeRequests
                                        .requestMatchers("/login/**", "/api/v1/auth/signin/**", "/api/v1/auth/signup/**", "/api/v1/oauth2/google/**", "/oauth2/**").permitAll()
                                        .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()
                                        .anyRequest().authenticated()
//                                          .anyRequest().permitAll()

                )
                .sessionManagement(sessionManagement ->
                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .oauth2Login(oauth2Login ->
                        oauth2Login.userInfoEndpoint(userInfoEndpointConfig -> userInfoEndpointConfig.userService(principalOAuth2DetailsService))
                                .successHandler(new OAuth2SuccessHandler(jwtTokenProvider, authService))
                )
                .exceptionHandling(exceptionHandling ->
                        exceptionHandling
                                .authenticationEntryPoint(new CustomAuthenticationEntryPoint())
                                .accessDeniedHandler(new CustomAccessDeniedHandler())
                );
        return http.build();
    }
}
