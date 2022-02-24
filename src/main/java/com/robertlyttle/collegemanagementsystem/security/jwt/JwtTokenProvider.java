package com.robertlyttle.collegemanagementsystem.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.robertlyttle.collegemanagementsystem.appuser.AppUser;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JwtTokenProvider {

    private static final long EXPIRATION_TIME = 432_000_000;
    private static final String ISSUER = "Belfast College";
    private static final String AUTHORITIES = "Authorities";
    private static final String AUDIENCE = "College Management System";
    private static final String TOKEN_CANNOT_BE_VERIFIED = "Token cannot be verified";

    @Value("{jwt.secret}")
    private String secret;

    public String generateJwtToken(AppUser user) {
        List<String> claims = getClaimsFromUser(user);
        return JWT.create()
                .withIssuer(ISSUER)
                .withAudience(AUDIENCE)
                .withIssuedAt(new Date())
                .withSubject(user.getUsername())
                .withArrayClaim(AUTHORITIES, claims.toArray(new String[0]))
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME)) // 5 days in milliseconds
                .sign(Algorithm.HMAC512(secret.getBytes()));
    }

    public String getSubject(String token) {
        return jwtVerifier().verify(token).getSubject();
    }

    public List<GrantedAuthority> getAuthorities(String token) {
        List<String> claims = getClaimsFromToken(token);
        return claims.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    }

    public Authentication getAuthentication(String username,
                                            List<GrantedAuthority> authorities,
                                            HttpServletRequest request) {
        UsernamePasswordAuthenticationToken authToken =
                new UsernamePasswordAuthenticationToken(
                        username,
                        null,
                        authorities);
        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        return authToken;
    }

    public boolean isTokenValid(String username, String token) {
        return StringUtils.isNotBlank(username) && !isTokenExpired(token);
    }

    public boolean isTokenExpired(String token) {
        return jwtVerifier().verify(token).getExpiresAt().before(new Date());
    }

    private List<String> getClaimsFromUser(AppUser user) {
        List<String> authorities = new ArrayList<>();
        for (GrantedAuthority grantedAuthority : user.getAuthorities()) {
            authorities.add(grantedAuthority.getAuthority());
        }
        return authorities;
    }

    private List<String> getClaimsFromToken(String token) {
        return jwtVerifier().verify(token).getClaim(AUTHORITIES).asList(String.class);
    }

    private JWTVerifier jwtVerifier() {
        JWTVerifier verifier;
        try {
            verifier = JWT.require(Algorithm.HMAC512(secret))
                    .withIssuer(ISSUER).build();
        } catch (JWTVerificationException e) {
            throw new JWTVerificationException(TOKEN_CANNOT_BE_VERIFIED);
        }
        return verifier;
    }
}
