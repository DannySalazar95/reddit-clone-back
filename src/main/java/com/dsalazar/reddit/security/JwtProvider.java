package com.dsalazar.reddit.security;

import com.dsalazar.reddit.exceptions.SpringRedditException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.security.*;
import java.time.Instant;
import java.util.Date;

@Service
public class JwtProvider {

    private KeyPair keyPair;

    @Value("${jwt.expiration.time}")
    private Long jwtExpirationInMillis;

    @PostConstruct
    public void init()
    {
        keyPair = Keys.keyPairFor(SignatureAlgorithm.RS256);
    }

    public String generateToken(Authentication authentication)
    {
        User principal = (User) authentication.getPrincipal();
        return Jwts.builder()
                .setSubject(principal.getUsername())
                .setIssuedAt(java.util.Date.from(Instant.now()))
                .setExpiration(java.util.Date.from(Instant.now().plusSeconds(jwtExpirationInMillis)))
                .signWith(getPrivateKey())
                .compact();
    }

    private PrivateKey getPrivateKey()
    {
        return keyPair.getPrivate();
    }

    public String generateTokenWithUserName(String username)
    {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(java.util.Date.from(Instant.now()))
                .signWith(getPrivateKey())
                .setExpiration(Date.from(Instant.now().plusSeconds(jwtExpirationInMillis)))
                .compact();
    }

    public boolean validateToken(String jwt)
    {
        Jwts.parser().setSigningKey(getPublicKey()).parseClaimsJws(jwt);
        return true;
    }

    private PublicKey getPublicKey() {
        return keyPair.getPublic();
    }

    public String getUsernameFromJwt(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(getPublicKey())
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }

    public Long getJwtExpirationInMillis() {
        return jwtExpirationInMillis;
    }

}
