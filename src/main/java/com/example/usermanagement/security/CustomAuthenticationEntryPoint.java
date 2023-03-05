package com.example.usermanagement.security;

import com.example.usermanagement.dto.response.EntryPointErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;

/**
 * 인증이 실패한 상황을 처리하는 AuthenticationEntryPoint 인터페이스를 구현한 클래스
 */
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
            throws IOException, ServletException {
        ObjectMapper objectMapper = new ObjectMapper();
        EntryPointErrorResponse entryPointErrorResponse = new EntryPointErrorResponse();
        entryPointErrorResponse.setMessage("인증 실패");

        response.setStatus(401);        // 401 Unathorized: 클라이언트의 전송한 유효하지 않은 credential로 인해 인증에 실패했음을 의미하는 에러 코드
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(objectMapper.writeValueAsString(entryPointErrorResponse));

    }
}
