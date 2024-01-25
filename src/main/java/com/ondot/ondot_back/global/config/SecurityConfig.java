package com.ondot.ondot_back.global.config;

import com.ondot.ondot_back.domain.organization.enums.OrganizationType;
import com.ondot.ondot_back.global.config.auth.AuthService;
import com.ondot.ondot_back.global.config.jwt.JwtTokenProvider;
import com.ondot.ondot_back.global.config.jwt.handler.CustomAccessDeniedHandler;
import com.ondot.ondot_back.global.config.jwt.handler.CustomAuthenticationEntryPoint;
import com.ondot.ondot_back.global.config.jwt.handler.OAuth2SuccessHandler;
import com.ondot.ondot_back.global.config.oauth.PrincipalOAuth2DetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity //스프링 시큐리티 필터가 스프링 필터페인에 등록
public class SecurityConfig {
    private final PrincipalOAuth2DetailsService principalOAuth2DetailsService;
    private final AuthService authService;
    private final JwtTokenProvider jwtTokenProvider;

    public SecurityConfig(PrincipalOAuth2DetailsService principalOAuth2DetailsService, AuthService authService, JwtTokenProvider jwtTokenProvider) {
        this.principalOAuth2DetailsService = principalOAuth2DetailsService;
        this.authService = authService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Bean
    public OrganizationType organizationType() {
        return OrganizationType.STUDENT_COUNCIL; // 여기서 기본 타입을 선택할 수 있습니다.
    }

    @Bean
    AuthenticationManager authenticationManager(
            AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean //패스워드 암호화 관련 메소드
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean // 시큐리티 설정 담당 메소드
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf((csrfConfig) -> csrfConfig.disable())
                .authorizeHttpRequests((authorizeRequests) ->
                                authorizeRequests
                                        .requestMatchers("/login/**", "/api/v1/auth/signin/**", "/api/v1/auth/signup/**", "/api/v1/oauth2/google/**", "/oauth2/**").permitAll()
                                        .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()
//                                .requestMatchers("/admin/**").hasAnyRole("ROLE_ADMIN")
                                        .anyRequest().authenticated() // 나머지 요청들은 인증
                )
                .sessionManagement(sessionManagement ->
                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                // 소셜 로그인 설정 - 구글 로그인이 완료된 뒤의 후처리가 필요함
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
