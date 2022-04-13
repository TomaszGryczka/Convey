package com.github.tomaszgryczka.convey.authentication.jwt;

import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Slf4j
@Component
public class JwtTokenUtil {
    
    private JwtConfig jwtConfig;

    @Autowired
    public JwtTokenUtil(JwtConfig jwtConfig) {
        this.jwtConfig = jwtConfig;
    }

    public boolean validateToken(String authToken) {
        try {
            Jwts.parser()
                    .setSigningKey(jwtConfig.getSecret().getBytes())
                    .parseClaimsJws(authToken);

            return true;
        } catch (MalformedJwtException e) {
            log.error("Invalid JWT Token mal");
        } catch (SignatureException e) {
            log.error("Invalid JWT Token signature");
        } catch (ExpiredJwtException e) {
            log.error("Expired JWT Token expired");
        } catch (IllegalArgumentException e) {
            log.error("Invalid JWT Token illegal");
        } catch (UnsupportedJwtException e) {
            log.error("Invalid JWT Token unsuppoerted");
        }

        return false;
    }

    public Claims getClaims(String authToken) {
        return Jwts.parser()
                .setSigningKey(jwtConfig.getSecret().getBytes())
                .parseClaimsJws(authToken)
                .getBody();
    }

    public String generateToken(Authentication authentication) {

        Long now = System.currentTimeMillis();

        return Jwts.builder()
                .setSubject(authentication.getName())
                .claim("authorities", authentication.getAuthorities())
                .setIssuedAt(new Date(now))
                .setExpiration(new Date(now + jwtConfig.getExpiration() * 1000))
                .signWith(SignatureAlgorithm.HS512, jwtConfig.getSecret().getBytes())
                .compact();
    }
}
