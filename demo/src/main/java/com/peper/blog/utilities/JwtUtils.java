package com.peper.blog.utilities;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtUtils {

    private final String privateKey;
    private long jwtTokenExpiration;
    private static final long DEFAULT_EXPIRATION = 30;
    private static final long MINUTE_IN_MILLISECONDS = 60_000 ;

    public JwtUtils(){
        this.privateKey = System.getProperty("private.key");

        try{
            this.jwtTokenExpiration = Long.parseLong(System.getProperty("jwt.expiration.minutes"));
        } catch (NumberFormatException e){
            this.jwtTokenExpiration = DEFAULT_EXPIRATION;
            e.printStackTrace();
        }
    }

    public JwtUtils(String privateKey, Long jwtTokenExpiration){
        this.privateKey = privateKey;
        this.jwtTokenExpiration = jwtTokenExpiration;
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(privateKey).parseClaimsJws(token).getBody();
    }

    public Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, userDetails.getUsername());
    }


    private String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + MINUTE_IN_MILLISECONDS * jwtTokenExpiration))
                .signWith(SignatureAlgorithm.HS256, privateKey).compact();
    }


    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

}
