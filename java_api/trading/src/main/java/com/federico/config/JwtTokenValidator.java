package com.federico.config;

import java.io.IOException;
import java.util.List;

import javax.crypto.SecretKey;

//import org.springframework.boot.autoconfigure.couchbase.CouchbaseProperties.Authentication;

import org.springframework.security.core.Authentication;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtTokenValidator extends OncePerRequestFilter {
 
    @Override
protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
        throws ServletException, IOException {
    
    String path = request.getServletPath();
    
    // Skip JWT validation for non-secured paths
    if (!path.startsWith("/api/")) {
        filterChain.doFilter(request, response);
        return;
    }

    String jwt = request.getHeader(JwtConstant.JWT_HEADER);

    if (jwt != null && jwt.startsWith("Bearer ")) {
        jwt = jwt.substring(7); // Remove "Bearer " prefix
    } else {
        filterChain.doFilter(request, response); // Skip if no token is present
        return;
    }

    try {
        SecretKey key = Keys.hmacShaKeyFor(JwtConstant.SECRET_KEY.getBytes());

        Claims claims = Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(jwt)
                .getPayload();
        
        String email = String.valueOf(claims.get("email"));
        String authorities = String.valueOf(claims.get("authorities"));

        List<GrantedAuthority> authoritiesList = AuthorityUtils.commaSeparatedStringToAuthorityList(authorities);
        
        Authentication auth = new UsernamePasswordAuthenticationToken(
            email, 
            null, 
            authoritiesList
        );

        SecurityContextHolder.getContext().setAuthentication(auth);
    } catch (Exception e) {
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid token"); // More informative error
        return;
    }

    filterChain.doFilter(request, response);
}
}