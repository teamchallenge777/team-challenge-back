package team.challenge.MobileStore.service.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import team.challenge.MobileStore.service.JwtTokenService;

import java.time.Duration;
import java.time.Instant;

@Service
public class JwtTokenServiceImpl implements JwtTokenService {
    private static final Duration JWT_TOKEN_VALIDITY = Duration.ofMinutes(20);
    private final Algorithm hmac512;
    private final JWTVerifier verifier;
    public JwtTokenServiceImpl(@Value("${jwt.secret}") final String secret) {
        this.hmac512 = Algorithm.HMAC512(secret);
        this.verifier = JWT.require(this.hmac512).build();
    }
    @Override
    public String generateToken(@NonNull UserDetails userDetails) {
        final Instant now = Instant.now();
        return JWT.create()
                .withSubject(userDetails.getUsername())
                .withIssuer("app")
                .withIssuedAt(now)
                .withExpiresAt(now.plusMillis(JWT_TOKEN_VALIDITY.toMillis()))
                .sign(this.hmac512);
    }

    @Override
    public String validateTokenAndGetUsername(@NonNull String token) {
        try{
            return verifier.verify(token).getSubject();
        } catch (JWTVerificationException e){
            return null;
        }
    }
}
