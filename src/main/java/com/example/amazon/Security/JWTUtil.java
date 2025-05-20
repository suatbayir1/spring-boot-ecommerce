package com.example.amazon.Security;

import com.example.amazon.Configuration.SecretKeyReader;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

public class JWTUtil {
    private JWTUtil() {
    }

    public static String generateToken(String username) {
        return Jwts
                .builder()
                .subject(username)
                .expiration(new Date(System.currentTimeMillis() + 3_600_000))
                .signWith(getSecretKey())
                .compact();
    }

    public static boolean validateToken(String token) {
        getAllClaimsFromToken(token);
        return true;
    }

    private static Claims getAllClaimsFromToken(String token) {
        return Jwts
                .parser()
                .verifyWith(getSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public static String extractUsername(String token) {
        Claims claims = getAllClaimsFromToken(token);
        return claims.getSubject();
    }

    private static SecretKey getSecretKey() {
        return Keys.hmacShaKeyFor(SecretKeyReader.getSecretKeyProperty().getBytes(StandardCharsets.UTF_8));
    }
}
