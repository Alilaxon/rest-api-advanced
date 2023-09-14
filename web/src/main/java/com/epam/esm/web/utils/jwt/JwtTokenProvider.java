package com.epam.esm.web.utils.jwt;

import com.epam.esm.web.DTO.JwtResponse;
import com.epam.esm.web.controller.AuthController;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JwtTokenProvider {

    private static final Logger log = LoggerFactory.getLogger(JwtTokenProvider.class);

    private final String secret;

    private final long jwtLifetime;

    private final JwtParser parser;

    public JwtTokenProvider(@Value("${jwt.secret}") String secret,
                            @Value("${jwt.lifetime}") long jwtLifetime) {
        this.secret = secret;
        this.jwtLifetime = jwtLifetime;
        this.parser = Jwts.parser()
                .setSigningKey(secret);
    }

    public JwtResponse generateToken(String username, Collection<? extends GrantedAuthority> roles) {

        List<String> rolesList = roles.stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
        return generateToken(username, rolesList);
    }

    public JwtResponse generateToken(String username, List<String> roles) {
        Date issuedDate = new Date();
        Date expiredDate = new Date(issuedDate.getTime() + (jwtLifetime * 60 * 1000));
        String token = Jwts.builder().claim("roles", roles)
                .setSubject(username)
                .setIssuedAt(issuedDate)
                .setExpiration(expiredDate)
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
        String refreshToken =
                Jwts.builder().setIssuedAt(issuedDate)
                        .setExpiration(expiredDate)
                        .signWith(SignatureAlgorithm.HS256, secret)
                        .compact();
        return new JwtResponse(token, refreshToken);
    }

    public JwtResponse refreshToken(String accessToken, String refreshToken) {
        Claims claims = getAllClaimsFromToken(accessToken);
        String username = getUsername(claims);
        List<String> rolesList = getRoles(claims);
        getAllClaimsFromToken(refreshToken);
        return generateToken(username, rolesList);
    }

    public String getUsername(String token) {
        return getUsername(getAllClaimsFromToken(token));
    }

    public String getUsername(Claims claims) {
        return claims.getSubject();
    }

    public List<String> getRoles(String token) {
        return getRoles(getAllClaimsFromToken(token));
    }

    public List<String> getRoles(Claims claims) {
        return claims.get("roles", List.class);
    }

    private Claims getAllClaimsFromToken(String token) {
        return parser.parseClaimsJws(token).getBody();
    }
}
