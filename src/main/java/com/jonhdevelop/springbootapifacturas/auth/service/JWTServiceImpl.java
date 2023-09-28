package com.jonhdevelop.springbootapifacturas.auth.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jonhdevelop.springbootapifacturas.auth.SimpleGrantedAuthorityMixin;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.util.Base64Utils;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;

@Component
public class JWTServiceImpl implements JWTService{

    public static final String SECRET = Base64Utils.encodeToString("Alguna.Clave.Secreta.123456".getBytes());

    public static final long EXPIRATION = 3600000L; // 1 hora

    public static final String PREFIJO = "Bearer ";

    public static final  String HEADER = "Authorization";


    @Override
    public String createToken(Authentication authentication) throws IOException {
        String username = ((User) authentication.getPrincipal()).getUsername();
        Collection<? extends GrantedAuthority> roles = authentication.getAuthorities();
        Claims claims = Jwts.claims();
        claims.put("authorities", new ObjectMapper().writeValueAsString(roles));
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION)) //1 hora de expiracion
                .compact();
    }

    @Override
    public boolean validateToken(String token) {
        try{
            getClaimsToken(token);
            return true;
        } catch (JwtException e){
            return false;
        }
    }

    @Override
    public Claims getClaimsToken(String token) {
        return Jwts.parser().setSigningKey("Alguna.Clave.Secreta.123456".getBytes())
                .parseClaimsJws(resolve(token)).getBody();
    }

    @Override
    public String getUsernameToken(String token) {
        return getClaimsToken(token).getSubject();
    }

    @Override
    public Collection<? extends GrantedAuthority> getRoles(String token) throws IOException {
        Object roles = getClaimsToken(token).get("authorities");
         return Arrays.asList(new ObjectMapper()
                .addMixIn(SimpleGrantedAuthority.class, SimpleGrantedAuthorityMixin.class)
                .readValue(roles.toString().getBytes(), SimpleGrantedAuthority[].class));
    }

    @Override
    public String resolve(String token) {
        if(token != null && token.startsWith(PREFIJO)){
            return token.replace("Bearer", "");
        } else {
            return null;
        }
    }
}
