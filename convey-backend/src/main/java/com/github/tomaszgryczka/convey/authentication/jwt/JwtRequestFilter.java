package com.github.tomaszgryczka.convey.authentication.jwt;

import com.github.tomaszgryczka.convey.user.MyUserDetailsService;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    private final JwtConfig jwtConfig;

    private final JwtTokenUtil jwtTokenUtil;

    private final MyUserDetailsService myUserDetailsService;

    @Autowired
    public JwtRequestFilter(JwtConfig jwtConfig, JwtTokenUtil jwtTokenUtil, MyUserDetailsService myUserDetailsService) {
        this.jwtConfig = jwtConfig;
        this.jwtTokenUtil = jwtTokenUtil;
        this.myUserDetailsService = myUserDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        final String authHeader = request.getHeader(jwtConfig.getHeader());

        if (authHeader != null && authHeader.startsWith(jwtConfig.getPrefix())) {

            String authToken = authHeader.replace(jwtConfig.getPrefix(), "");

            if (jwtTokenUtil.validateToken(authToken)) {
                Claims claims = jwtTokenUtil.getClaims(authToken);

                String username = claims.getSubject();

                UserDetails userDetails = myUserDetailsService.loadUserByUsername(username);

                UsernamePasswordAuthenticationToken usrPswdToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());

                usrPswdToken.setDetails(new WebAuthenticationDetails(request));

                SecurityContextHolder.getContext().setAuthentication(usrPswdToken);
            }

            filterChain.doFilter(request, response);
        } else {
            filterChain.doFilter(request, response);

            return;
        }

        return;
    }
}
