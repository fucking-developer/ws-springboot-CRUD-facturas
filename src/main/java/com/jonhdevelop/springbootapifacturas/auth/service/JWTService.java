package com.jonhdevelop.springbootapifacturas.auth.service;

import io.jsonwebtoken.Claims;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.io.IOException;
import java.util.Collection;

public interface JWTService {

    String createToken(Authentication authentication) throws IOException;

    boolean validateToken(String token);

    Claims getClaimsToken(String token);

    String getUsernameToken(String token);

    Collection<? extends GrantedAuthority> getRoles(String token) throws IOException;

    String resolve(String token);

}
