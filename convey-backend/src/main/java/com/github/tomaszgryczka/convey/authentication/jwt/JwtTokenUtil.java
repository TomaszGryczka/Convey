package com.github.tomaszgryczka.convey.authentication.jwt;

import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class JwtTokenUtil {

    @Autowired
    private JwtConfig jwtConfig;

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
}
