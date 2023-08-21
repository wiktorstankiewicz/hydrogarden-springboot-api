package com.hydrogarden.server.security;

import com.hydrogarden.server.services.UserService;
import com.hydrogarden.server.services.JwtService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.filter.OncePerRequestFilter;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.Arrays;

import io.jsonwebtoken.security.SignatureException;
import org.springframework.web.server.ResponseStatusException;


@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");
        String jwtToken;
        boolean successful;
        if (request.getCookies() == null) {
            filterChain.doFilter(request, response);
            return;
        }
        Cookie authCookie = Arrays.stream(request.getCookies()).filter(ck -> ck.getName().equals("Authorization")).findFirst().orElse(null);
        if (!StringUtils.isEmpty(authHeader) && StringUtils.startsWith(authHeader, "Bearer ")) {
            jwtToken = authHeader.substring(7);
            successful = attemptAuthentication(request, response, filterChain, jwtToken);
            if (successful) {
                filterChain.doFilter(request, response);
                return;
            }
        }
        if (authCookie != null && !StringUtils.isEmpty(authCookie.getValue())) {
            jwtToken = authCookie.getValue();
            successful = attemptAuthentication(request, response, filterChain, jwtToken);
            if (successful) {
                filterChain.doFilter(request, response);
                return;
            }
        } else {
            filterChain.doFilter(request, response);
        }


    }

    private boolean attemptAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain, String jwtToken) throws IOException, ServletException {
        String username;
        boolean isValid = false;
        boolean isExpired = true;
        try {
            username = jwtService.extractUsername(jwtToken);
            isValid = jwtService.isTokenValid(jwtToken);
            isExpired = jwtService.isTokenExpired(jwtToken);
        } catch (MalformedJwtException |
                 SignatureException | ExpiredJwtException e) {
            return false;
        }
        if (isValid && !isExpired) {
            UserDetails userDetails = userService.userDetailsService().loadUserByUsername(username);

            SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
            UsernamePasswordAuthenticationToken userAuthToken = new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(), userDetails.getAuthorities());
            userAuthToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            securityContext.setAuthentication(userAuthToken);
            SecurityContextHolder.setContext(securityContext);
            return true;
        }
        return false;
    }
}
