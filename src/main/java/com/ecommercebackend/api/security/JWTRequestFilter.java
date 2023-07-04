package com.ecommercebackend.api.security;

import com.auth0.jwt.exceptions.JWTDecodeException;
import com.ecommercebackend.model.LocalUser;
import com.ecommercebackend.model.dao.LocalUserDAO;
import com.ecommercebackend.service.JWTService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;


// Filter for decoding a JWT in the Authorization header and loading the user
// object into the authentication context.

@Component
public class JWTRequestFilter extends OncePerRequestFilter {

    private JWTService jwtService;
    private LocalUserDAO localUserDAO;

    public JWTRequestFilter(JWTService jwtService, LocalUserDAO localUserDAO) {
        this.jwtService = jwtService;
        this.localUserDAO = localUserDAO;
    }


    // everytime we get request - its going to below method
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String tokenHeader = request.getHeader("Authorization");   //check if it have header called auth-n
        if (tokenHeader != null && tokenHeader.startsWith("Bearer ")) {    //check if it starts with Bearer
            String token = tokenHeader.substring(7);
            try {
                String username = jwtService.getUsername(token);    // try to decode a token
                Optional<LocalUser> opUser = localUserDAO.findByUsernameIgnoreCase(username);   // then taking usernmae and trying to find the user
                if (opUser.isPresent()) {     // in case user is present.
                    LocalUser user = opUser.get();    // then we build an AUTH-N object of the user
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user, null, new ArrayList());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            } catch (JWTDecodeException ex) {    // if we cant decode a token - we do nothing

            }

        }
        filterChain.doFilter(request, response);
    }



}
