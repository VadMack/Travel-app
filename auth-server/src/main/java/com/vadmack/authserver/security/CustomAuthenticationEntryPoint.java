package com.vadmack.authserver.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    ObjectMapper objectMapper;

    @PostConstruct
    private void postConstruct() {
        objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();
    }

    @Override
    public void commence(HttpServletRequest req, HttpServletResponse res, AuthenticationException authException) throws IOException {
        res.setContentType("application/json;charset=UTF-8");
        res.setStatus(403);
        res.getWriter().write(objectMapper.writeValueAsString(new ForbiddenStatusBody("Access denied")));
    }
}