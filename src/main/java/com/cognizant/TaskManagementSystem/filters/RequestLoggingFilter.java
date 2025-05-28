package com.cognizant.TaskManagementSystem.filters;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class RequestLoggingFilter extends OncePerRequestFilter {
    private static final Logger logger = LoggerFactory.getLogger(RequestLoggingFilter.class);
    private static final String LOGGED_ATTRIBUTE = "requestLogged";

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {

        if (request.getAttribute(LOGGED_ATTRIBUTE) == null) {
            String username = SecurityContextHolder.getContext().getAuthentication() != null ?
                    SecurityContextHolder.getContext().getAuthentication().getName() : "anonymous";

            logger.warn("Request: {} {} from user '{}' with IP '{}'",
                    request.getMethod(),
                    request.getRequestURI(),
                    username,
                    request.getRemoteAddr()
            );

            request.setAttribute(LOGGED_ATTRIBUTE, true);
        }

        filterChain.doFilter(request, response);

        logger.warn("Response: {} for {} with status {}",
                request.getMethod(),
                request.getRequestURI(),
                response.getStatus()
        );

    }
}


