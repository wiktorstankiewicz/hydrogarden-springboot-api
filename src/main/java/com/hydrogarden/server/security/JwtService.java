package com.hydrogarden.server.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

@Service
@AllArgsConstructor
public class JwtService {
    public static int EXPIRATION_TIME = 1000*60*24;
    public static String SIGNING_KEY = "VERYSECRET";
    public boolean isTokenValid(String jwt){
        return Jwts.parser().setSigningKey(getSigningKey()).isSigned(jwt);
    }
    public String extractUsername(String jwt){
        return extractClaim(jwt,Claims::getSubject);
    }
    public <T> T extractClaim(String jwt, Function<Claims, T> claimsResolver){
        Claims claims = extractAllClaims(jwt);
        return claimsResolver.apply(claims);
    }
    public String generateToken(Map<String,Object> claims, UserDetails userDetails){
        Date now = new Date(System.currentTimeMillis());
        Date expiresAt = new Date(System.currentTimeMillis() + EXPIRATION_TIME);
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(now)
                .setExpiration(expiresAt)
                .signWith(SignatureAlgorithm.HS256, getSigningKey())
                .compact();
    }
    public boolean isTokenExpired(String jwt){
        Date expDate = extractClaim(jwt,Claims::getExpiration);
        Date now = new Date(System.currentTimeMillis());
        return now.before(expDate);
    }
    public Claims extractAllClaims(String jwt){
        return Jwts.parser().setSigningKey(getSigningKey()).parseClaimsJwt(jwt).getBody();
    }
    public Key getSigningKey(){
        byte[] keyBytes = Decoders.BASE64.decode(SIGNING_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
