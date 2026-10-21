package ma.enset.tp2_jwt_demo.service;

import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Base64.Decoder;

import javax.crypto.SecretKey;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;

@Service 
public class JwtService {
    @Value("${jwt.secret}") 
    private String jwtSecret;
    @Value("${jwt.expiration}")
    private Long jwtExpiration;

    private SecretKey getSecretKey(){
        byte [] keyBytes = Decoders.BASE64.decode(jwtSecret);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateToken(UserDetails userDetails){
        return Jwts.builder()
                .subject(userDetails.getUsername())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis()+jwtExpiration))
                .signWith(getSecretKey())
                .compact();
    }
    public String extractUsername(String token){
        return extractAllClaimsFromToken(token).getSubject();
    }

    public Claims extractAllClaimsFromToken(String token){
        return Jwts.parser()
        .verifyWith(getSecretKey())
        .build()
        .parseSignedClaims(token)
        .getPayload();
    }

    public boolean isTokenValid(String token, UserDetails userDetails){
        String userName=extractUsername(token);
        return userDetails.getUsername().equals(userName);
    }

    public boolean isTokenExpired(String token){
        Date expiration= extractAllClaimsFromToken(token).getExpiration();
        return expiration.before(new Date());
    }






}
