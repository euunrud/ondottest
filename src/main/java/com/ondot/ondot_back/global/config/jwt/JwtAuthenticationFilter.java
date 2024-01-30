//package com.ondot.ondot_back.global.config.jwt;
//
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import lombok.RequiredArgsConstructor;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//
//@RequiredArgsConstructor
//public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter { //누구인지
//        private final AuthenticationManager authenticationManager;
//
//        //login 요청하면 로그인 시도를 위해서 실행되는 함수
//    @Override
//    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
//        //1. username, password를 받아서
//        //2. 정상인지 로그인 시도 PrinciaplDetailsService호출 loadUserByUsername();
//        //3. PricincialDetails를 세션에 담고
//        //4. Jwt토큰을 만들어서 응답
//        return super.attemptAuthentication(request, response);
//    }
//}
