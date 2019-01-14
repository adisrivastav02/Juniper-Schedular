package com.iig.gcp;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

	public static final String SECRET = "SecretKeyToGenJWTs";

    @Override
    public Authentication authenticate(Authentication authentication)
            throws AuthenticationException {
        String name = authentication.getName();
        Object credentials = authentication.getCredentials();
        System.out.println("credentials class: " + credentials.getClass());
        if (!(credentials instanceof String)) {
            return null;
        }
        String password = credentials.toString();

        String token = credentials.toString();
		
		DecodedJWT decodedToken = JWT.decode(token);
        JWTVerifier verifier = selectVerifier(decodedToken);
        DecodedJWT decodedJWT = verifier.verify(token);
	    System.out.println("User" + decodedJWT.getSubject());

        if (decodedJWT.getSubject()== null) {
            throw new BadCredentialsException("Authentication failed for " + name);
        }

        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        grantedAuthorities.add(new SimpleGrantedAuthority("ADMIN"));
        Authentication auth = new
                UsernamePasswordAuthenticationToken(name, password, grantedAuthorities);
        return auth;
    }

    
    private JWTVerifier selectVerifier(DecodedJWT decodedToken) {
        String algorithm = decodedToken.getAlgorithm();
        switch (algorithm) {
            case "HS512":
            	Algorithm algorithm512 = Algorithm.HMAC512(SECRET.getBytes());
            	return JWT
                .require(algorithm512)
                .build();
                 
            default:
                throw new IllegalStateException("Cannot verify against algorithm: " + algorithm);
        }
    }
    
    
    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }

}