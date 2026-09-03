package com.telecom.api.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    public JwtAuthenticationFilter(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");

        System.out.println("====================================");
        System.out.println("REQUEST: " + request.getRequestURI());
        System.out.println("AUTH HEADER PRESENT: " + (authHeader != null));

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {

            System.out.println("NO JWT TOKEN");

            filterChain.doFilter(request, response);
            return;
        }

        String token = authHeader.substring(7);

        try {

            if (jwtService.isValid(token)) {

                String username =
                        jwtService.extractUsername(token);

                System.out.println("JWT VALID");
                System.out.println("USERNAME: " + username);

                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(
                                username,
                                null,
                                AuthorityUtils.NO_AUTHORITIES
                        );

                SecurityContextHolder
                        .getContext()
                        .setAuthentication(authentication);

                System.out.println(
                        "AUTHENTICATION SET: "
                        + SecurityContextHolder
                                .getContext()
                                .getAuthentication()
                );

            } else {

                System.out.println("JWT INVALID");
            }

        } catch (Exception e) {

            System.out.println("JWT ERROR");
            System.out.println("ERROR TYPE: " + e.getClass().getName());
            System.out.println("ERROR MESSAGE: " + e.getMessage());
        }

        filterChain.doFilter(request, response);
    }
}