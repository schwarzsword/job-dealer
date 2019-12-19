package edu.netcracker.jobdealer.config.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class MyAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException, ServletException {
        response.addHeader("Authorization", BCrypt.hashpw(authentication.getName(), BCrypt.gensalt()));
        response.setHeader("Authorization", BCrypt.hashpw(authentication.getName(), BCrypt.gensalt()));
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        httpServletResponse.addHeader("Authorization", BCrypt.hashpw(authentication.getName(), BCrypt.gensalt()));
        httpServletResponse.setHeader("Authorization", BCrypt.hashpw(authentication.getName(), BCrypt.gensalt()));
    }
}
