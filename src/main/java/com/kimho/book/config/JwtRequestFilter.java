package com.kimho.book.config;

import com.kimho.book.service.impl.UserDetailServiceImpl;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.kimho.book.model.secutiry.Constants.HEADER_STRING;
import static com.kimho.book.model.secutiry.Constants.TOKEN_PREFIX;

@Component
@Configuration
public class JwtRequestFilter extends OncePerRequestFilter {
    @Autowired
    private UserDetailServiceImpl userDetailServiceImpl;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String header = request.getHeader(HEADER_STRING);
        String email = null;
        String jwtToken = null;
        if (header != null && header.startsWith(TOKEN_PREFIX)) {
            jwtToken = header.replace(TOKEN_PREFIX, "");
            try {
                email = jwtTokenProvider.getUsernameFromToken(jwtToken);
            } catch (IllegalArgumentException e) {
                logger.error("an error occurred during getting username from token", e);
            } catch (ExpiredJwtException e) {
                logger.error("the token is expired and not valid anymore", e);
            } catch (MalformedJwtException e){
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            }
        } else {
            logger.warn("couldn't find bearer string, will ignore the header");
        }
        if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userDetailServiceImpl.loadUserByUsername(email);
            if (jwtTokenProvider.validateToken(jwtToken, userDetails)) {
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = jwtTokenProvider
                        .getAuthentication(jwtToken, SecurityContextHolder.getContext().getAuthentication(), userDetails);
                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                logger.info("authenticated user " + email + ", setting security context");
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }
        filterChain.doFilter(request, response);
    }
}