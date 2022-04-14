package com.github.tomaszgryczka.convey.config;

import com.github.tomaszgryczka.convey.login.jwttoken.exception.FilterChainExceptionHandler;
import com.github.tomaszgryczka.convey.login.jwttoken.JwtRequestFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.OncePerRequestFilter;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private OncePerRequestFilter jwtRequestFilter;

    private FilterChainExceptionHandler filterChainExceptionHandler;

    @Autowired
    public SecurityConfig(OncePerRequestFilter jwtRequestFilter,
                          FilterChainExceptionHandler filterChainExceptionHandler) {
        this.jwtRequestFilter = jwtRequestFilter;
        this.filterChainExceptionHandler = filterChainExceptionHandler;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/chat").authenticated()
                .antMatchers("/session").permitAll()
                .antMatchers("/users").permitAll()
                .and()
                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(filterChainExceptionHandler, JwtRequestFilter.class);

        http.cors().and().csrf().disable();
    }
}
