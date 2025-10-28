package com.example.EMS_backend.filter;


import io.jsonwebtoken.io.IOException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.EMS_backend.service.JwtService;
import com.example.EMS_backend.service.UserDetailsServiceImpl;


@Component
public class JwtAuthFilter extends OncePerRequestFilter {
    private static final Logger logger = LoggerFactory.getLogger(JwtAuthFilter.class);

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException, java.io.IOException {

    logger.info("Request URL: {}", request.getRequestURI());  // ✅ Log incoming request
    logger.info("Authorization header: {}", request.getHeader("Authorization"));

        String authHeader = request.getHeader("Authorization");
        String token = null;
        String username = null;

        logger.debug("Checking Authorization header");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7);
            username = jwtService.extractUsername(token);
            logger.info("Extracted username from token: {}", username);
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            logger.debug("No existing authentication found, validating token...");

            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            if (jwtService.validateToken(token, userDetails)) {
                logger.info("Token validated for user: {}", username);

                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails,
                        null, userDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
                logger.info("User {} authenticated and set in security context", username);
            }
            else {
                logger.warn("Token validation failed for user: {}", username);
            }
        }
        filterChain.doFilter(request, response);
        logger.debug("Filter chain continued");
    }

    @Override
protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
    String path = request.getServletPath();
    return path.startsWith("/swagger-ui")
            || path.startsWith("/v3/api-docs")
            || path.startsWith("/h2-console")
            || path.startsWith("/api/ems/users/auth/login")
            || path.startsWith("/api/ems/users/auth/register")
            || path.equals("/favicon.ico"); // skip favicon
}


}
