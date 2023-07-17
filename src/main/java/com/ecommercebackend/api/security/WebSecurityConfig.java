package com.ecommercebackend.api.security;

//Since we r getting 401 Unauthorized output by default(Spring security dependency). We r going to overwrite that

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.AuthorizationFilter;
import org.springframework.security.web.authentication.AuthenticationFilter;

@Configuration
public class WebSecurityConfig {

    private JWTRequestFilter jwtRequestFilter;

    public WebSecurityConfig(JWTRequestFilter jwtRequestFilter) {
        this.jwtRequestFilter = jwtRequestFilter;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable().cors().disable();
        // We need to make sure our authentication filter is run before the http request filter is run.
        http.addFilterBefore(jwtRequestFilter, AuthorizationFilter.class);
        AuthorizeHttpRequestsConfigurer<HttpSecurity>
                .AuthorizationManagerRequestMatcherRegistry authenticated
                = http.authorizeHttpRequests()
                // Specific exclusions or rules.
                .requestMatchers("/product", "/auth/signup", "/auth/login").permitAll()
                // Everything else should be authenticated.
                .anyRequest().authenticated();
        return http.build();
    }

}
