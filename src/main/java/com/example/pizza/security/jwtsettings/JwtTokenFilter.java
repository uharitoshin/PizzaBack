package com.example.pizza.security.jwtsettings;

import com.example.pizza.security.exception.JwtAuthenticationException;
import com.example.pizza.security.utils.JwtTokenUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

// На самом деле все равно кого экстендит, главное получить ServletRequest servletRequest, ServletResponse servletResponse
// чтобы из них вытащить инфу о хедере, где уже лежит токен
@Component
@RequiredArgsConstructor
public class JwtTokenFilter extends GenericFilterBean {

    private final JwtTokenUtils jwtTokenUtils;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        String token = jwtTokenUtils.resolveToken((HttpServletRequest) servletRequest);
        // если валидный то в контекс секьюрити записываем токен
        try {
            if (token != null && jwtTokenUtils.validateToken(token)) {
                Authentication authentication = jwtTokenUtils.getAuthentication(token);
                if (authentication != null) {
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        } catch (JwtAuthenticationException e) {
            SecurityContextHolder.clearContext();
            ((HttpServletResponse) servletResponse).sendError(e.getHttpStatus().value());
            throw new JwtAuthenticationException("JWT token is expired or invalid");
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }
}