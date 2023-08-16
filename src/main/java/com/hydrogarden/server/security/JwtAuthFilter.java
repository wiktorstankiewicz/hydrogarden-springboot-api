package com.hydrogarden.server.security;

import com.hydrogarden.server.services.JwtService;
import com.hydrogarden.server.services.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.Arrays;


@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserService userService;

    private final RequestMatcher ignoredPathsMatcher = new AntPathRequestMatcher("/auth/**");

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if(request.getCookies() == null){
            filterChain.doFilter(request,response);
            return;
        }
        Cookie authCookie = Arrays.stream(request.getCookies()).filter(cookie -> cookie.getName().equals("Authorisation")).findFirst().orElse(null);
        if(authCookie == null ){
            filterChain.doFilter(request,response);
            return;
        }
        String jwtToken = authCookie.getValue();
        String username = jwtService.extractUsername(jwtToken);
        boolean isValid = jwtService.isTokenValid(jwtToken);
        boolean isExpired = jwtService.isTokenExpired(jwtToken);
        if (isValid && !isExpired && username != null && !username.equals("")) {
            UserDetails userDetails = userService.userDetailsService().loadUserByUsername(username);

            SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
            UsernamePasswordAuthenticationToken userAuthToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            userAuthToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            securityContext.setAuthentication(userAuthToken);
            SecurityContextHolder.setContext(securityContext);
            filterChain.doFilter(request,response);
        }
        filterChain.doFilter(request,response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return ignoredPathsMatcher.matches(request);
    }
}
