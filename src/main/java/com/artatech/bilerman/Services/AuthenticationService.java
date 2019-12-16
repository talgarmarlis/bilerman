package com.artatech.bilerman.Services;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.Date;

@Service
public class AuthenticationService {

    static final Long EXPIRATION_TIME = 864_000_00L;
    static final String SIGNING_KEY = "bilerman_singing_key";
    static final String BEARER_PREFIX = "Bearer";

    static public void addJWTToken(HttpServletResponse response, String username){
        String jwtToken = Jwts.builder().setSubject(username)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, SIGNING_KEY)
                .compact();

        response.addHeader("Authorization", BEARER_PREFIX + " " + jwtToken);
        response.addHeader("Access-Control-Expose-Headers", "Authorization");
    }

    static public Authentication getAuthentication(HttpServletRequest request){
        String token = request.getHeader("Authorization");
        if(token != null) {
            String user = Jwts.parser()
                    .setSigningKey(SIGNING_KEY)
                    .parseClaimsJws(token.replace(BEARER_PREFIX, ""))
                    .getBody()
                    .getSubject();

            if(user == null) throw new RuntimeException("Authentication failed");

            return new UsernamePasswordAuthenticationToken(user, null, Collections.emptyList());
        }

        return null;
    }
}
