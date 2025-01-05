package com.dinhchieu.demo.utils;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtils {
    private SecretKey generateKey(){
        byte[] key = Base64.getDecoder().decode(Constants.HEX_KEY);
        return Keys.hmacShaKeyFor(key);
    }

    public String generateToken(UserDetails userDetails){
        Map<String, String> claims = new HashMap<>();

        return Jwts
                .builder()
                .claims(claims)
                .expiration(Date.from(Instant.now().plusMillis(Constants.VALIDITY)))
                .issuedAt(Date.from(Instant.now()))
                .subject(userDetails.getUsername())
                .signWith(generateKey())
                .compact();
    }

    public String generateRefreshToken(UserDetails userDetails){
        return Jwts.builder()
                .signWith(generateKey())
                .expiration(Date.from(Instant.now().plusMillis(Constants.REFRESH)))
                .issuedAt(Date.from(Instant.now()))
                .subject(userDetails.getUsername())
                .compact();
    }

   public Claims claims(String token){
       return Jwts.parser().verifyWith(generateKey()).build().parseSignedClaims(token).getPayload();
   }

    public String extractUsername(String token){
         return claims(token).getSubject();
    }

    // Check if the token is valid (The token is not expired, the signature is valid, the token is not empty)
    public boolean isTokenValid(String token){
        try {
            return claims(token).getExpiration().after(Date.from(Instant.now()));
        } catch (ExpiredJwtException ex) {
            throw new JwtException("Token is expired!");
        } catch (MalformedJwtException ex) {
            throw new JwtException("Token is invalid with structure!");
        } catch (SignatureException ex) {
            throw new JwtException("Token is invalid with signature!");
        } catch (IllegalArgumentException ex) {
            throw new JwtException("Token is empty or not existed!");
        } catch (JwtException ex) {
            throw new JwtException("Token is invalid!");
        }
    }
}
