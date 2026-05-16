package com.himanshu.test.middleware;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.himanshu.test.utils.LoggingService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class RequestLoggingFilter extends OncePerRequestFilter {

    @Autowired
    private LoggingService loggingService;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        String uri = request.getRequestURI();

        if (!uri.contains("/evaluation-service")) {
            loggingService.log(
                    "info",
                    "middleware",
                    "Incoming request: " +
                            request.getMethod() +
                            " " +
                            uri
            );
        }

        filterChain.doFilter(request, response);
    }
}