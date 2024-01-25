package com.ondot.ondot_back.global.config.jwt.handler;

import com.ondot.ondot_back.global.config.auth.AuthService;
import com.ondot.ondot_back.global.config.auth.PrincipalDetails;
import com.ondot.ondot_back.global.config.jwt.JwtTokenProvider;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;

@RequiredArgsConstructor
@Component
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthService authService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {
        PrincipalDetails oAuth2User = (PrincipalDetails) authentication.getPrincipal();
        String targetUrl;
        String accessToken =  jwtTokenProvider.createAccessToken(oAuth2User.getOrgaization().getId());
        String refreshToken =  jwtTokenProvider.createRefreshToken(oAuth2User.getOrgaization().getId());

        authService.registerRefreshToken(oAuth2User.getOrgaization().getId(),refreshToken);

        targetUrl = UriComponentsBuilder.fromUriString("/auth/oauth2/success")
                .queryParam("accessToken",accessToken)
                .queryParam("refreshToken",refreshToken)
                .build().toUriString();
        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }
}
