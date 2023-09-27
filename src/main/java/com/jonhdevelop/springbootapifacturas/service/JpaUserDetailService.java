package com.jonhdevelop.springbootapifacturas.service;

import com.jonhdevelop.springbootapifacturas.dao.UsuarioDao;
import com.jonhdevelop.springbootapifacturas.entity.Role;
import com.jonhdevelop.springbootapifacturas.entity.Usuario;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service("jpaUserDetailServiceImpl")
public class JpaUserDetailService implements UserDetailsService {

    @Autowired
    private UsuarioDao usuarioDao;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioDao.findByUsername(username);
        if(usuario == null){
            log.info("Usuario "+username+" no existe");
            throw new UsernameNotFoundException("Usuario "+username+" no existe");
        }
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        for(Role role: usuario.getRoles()){
            log.info("Role: " .concat(role.getAuthority()));
            authorities.add(new SimpleGrantedAuthority(role.getAuthority()));
        }
        if(authorities.isEmpty()){
            log.info("El usuario "+ username + " no tiene roles asignados");
            throw new UsernameNotFoundException("El usuario "+ username + " no tiene roles asignados");
        }
        return new User(usuario.getUsername(), usuario.getPassword(), usuario.getEnabled(), true, true, true, authorities);
    }
}
