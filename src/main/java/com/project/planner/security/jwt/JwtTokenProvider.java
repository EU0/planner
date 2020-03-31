package com.project.planner.security.jwt;

import com.project.planner.config.ApplicationProperties;
import com.project.planner.security.SecurityUserDetailService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.Base64;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Component
@Slf4j
public class JwtTokenProvider {

    private static final String AUTHORITIES_KEY = "auth";

    private String base64Secret;
    private long tokenValidity;
    private long tokenValidityForRememberMe;

    private SecurityUserDetailService securityUserDetailService;
    private final ApplicationProperties applicationProperties;

    @Autowired
    public JwtTokenProvider(SecurityUserDetailService securityUserDetailService,
                            ApplicationProperties applicationProperties) {
        this.securityUserDetailService = securityUserDetailService;
        this.applicationProperties = applicationProperties;
    }

    @PostConstruct
    public void init() {
        tokenValidity = 1000 * applicationProperties.getSecurity()
                .getAuthentication()
                .getTokenValidityInSeconds();
        tokenValidityForRememberMe = 1000 * applicationProperties.getSecurity()
                .getAuthentication()
                .getTokenValidityInSecondsForRememberMe();
        base64Secret = Base64.getEncoder().encodeToString(
                applicationProperties.getSecurity().getAuthentication()
                        .getBase64Secret().getBytes());
    }

    public String createToken(Authentication authentication, boolean rememberMe) {
        String authorities = authentication.getAuthorities()
                .stream().map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));


        Date validityDate;
        long now = new Date().getTime();
        if (rememberMe) {
            validityDate = new Date(now + tokenValidityForRememberMe);
        } else {
            validityDate = new Date(now + tokenValidity);
        }
        return Jwts.builder()
                .setSubject(authentication.getName())
                .claim(AUTHORITIES_KEY, authorities)
                .signWith(SignatureAlgorithm.HS256, base64Secret)
                .setExpiration(validityDate)
                .compact();
    }

    public Authentication getAuthentication(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(base64Secret)
                .parseClaimsJws(token)
                .getBody();

        Collection<? extends GrantedAuthority> grantedAuthorities =
                Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

        UserDetails userDetails = securityUserDetailService.loadUserByUsername(claims.getSubject());

        return new UsernamePasswordAuthenticationToken(userDetails, token, grantedAuthorities);
    }


    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(base64Secret).parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            log.info("Invalid JWT token.");
            log.trace("Invalid JWT token trace.", e);
        }
        return false;
    }
}
