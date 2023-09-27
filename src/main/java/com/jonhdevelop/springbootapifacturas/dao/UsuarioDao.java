package com.jonhdevelop.springbootapifacturas.dao;

import com.jonhdevelop.springbootapifacturas.entity.Usuario;
import org.springframework.data.repository.CrudRepository;

public interface UsuarioDao extends CrudRepository<Usuario, Long> {

    Usuario findByUsername(String username);

}
