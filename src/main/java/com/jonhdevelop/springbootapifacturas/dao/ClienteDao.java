package com.jonhdevelop.springbootapifacturas.dao;

import com.jonhdevelop.springbootapifacturas.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ClienteDao extends JpaRepository<Cliente, Long> {

    @Query("SELECT c FROM Cliente c LEFT JOIN FETCH c.facturas f WHERE c.id=?1")
    Cliente fetchByIdWithFacturas(Long id);
}
