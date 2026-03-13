package io.github.giannialberico;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class HttpLoggingFilter extends OncePerRequestFilter {

    HttpLoggerProperties httpLoggerProperties;

    public HttpLoggingFilter(HttpLoggerProperties httpLoggerProperties) {
        this.httpLoggerProperties = httpLoggerProperties;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        filterChain.doFilter(request, response);
    }
}