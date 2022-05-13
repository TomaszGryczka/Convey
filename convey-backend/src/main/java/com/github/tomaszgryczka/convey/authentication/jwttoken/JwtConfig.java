package com.github.tomaszgryczka.convey.authentication.jwttoken;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
public class JwtConfig {

    @Value("${jwt.secret:}")
    private String secret;

    @Value("${jwt.header:}")
    private String header;

    @Value("${jwt.prefix:}")
    private String prefix;

    @Value("${jwt.expiration:1800}")
    private int expiration;
}
