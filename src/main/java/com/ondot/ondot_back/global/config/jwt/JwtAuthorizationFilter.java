//package com.ondot.ondot_back.global.config.jwt;
//
//import java.io.IOException;
//
//import com.ondot.ondot_back.domain.organization.entity.Organization;
//import com.ondot.ondot_back.global.config.auth.PrincipalDetails;
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//
//import com.ondot.ondot_back.domain.organization.repository.OrganizationJpaRepository;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
//
//
//public class JwtAuthorizationFilter extends BasicAuthenticationFilter { //권환 jwt검
//    private OrganizationJpaRepository organizationRepository;
//
//    public JwtAuthorizationFilter(AuthenticationManager authenticationManager, OrganizationJpaRepository organizationRepository) {
//        super(authenticationManager);
//        this.organizationRepository = organizationRepository;
//    }
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
//        String header = request.getHeader(JwtProperties.HEADER_STRING);
//        if (header == null || !header.startsWith(JwtProperties.TOKEN_PREFIX)) {
//            chain.doFilter(request, response);
//            return;
//        }
////        System.out.println("header : " + header);
//
//        // jwt를 검증해서 정상적인 사용자인지 확인
//        String token = request.getHeader(JwtProperties.HEADER_STRING)
//                .replace(JwtProperties.TOKEN_PREFIX, "");
//
//        String organizationId = JWT.require(Algorithm.HMAC512(JwtProperties.SECRET)).build().verify(token)
//                .getClaim("organizationId").asString();
//
//        if (organizationId != null) {
//            Organization organization = organizationRepository.findSingleByOrganizationId(organizationId);
//
//            PrincipalDetails principalDetails = new PrincipalDetails(organization);
//            Authentication authentication =
//                    new UsernamePasswordAuthenticationToken(principalDetails, null, principalDetails.getAuthorities());
//
//            // 강제로 시큐리티의 세션에 접근하여 값 저장
//            SecurityContextHolder.getContext().setAuthentication(authentication);
//        }
//
//        chain.doFilter(request, response);
//    }
//}
//
