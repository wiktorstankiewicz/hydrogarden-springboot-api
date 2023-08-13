package com.hydrogarden.server.security;

import com.hydrogarden.server.services.JwtService;
import com.hydrogarden.server.services.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;


@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");
        String jwtToken;
        String username;
        if (StringUtils.isEmpty(authHeader) || !StringUtils.startsWith(authHeader, "Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }
        //7 is the length of string "Bearer "
        jwtToken = authHeader.substring(7);
        username = jwtService.extractUsername(jwtToken);
        boolean isValid = jwtService.isTokenValid(jwtToken);
        boolean isExpired = jwtService.isTokenExpired(jwtToken);
        if (isValid && !isExpired) {
            UserDetails userDetails = userService.userDetailsService().loadUserByUsername(username);

            SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
            UsernamePasswordAuthenticationToken userAuthToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            userAuthToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            securityContext.setAuthentication(userAuthToken);
            SecurityContextHolder.setContext(securityContext);
        }
        filterChain.doFilter(request,response);
    }
}
