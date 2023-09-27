package com.jonhdevelop.springbootapifacturas.dao;

import com.jonhdevelop.springbootapifacturas.entity.Factura;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface FacturaDao extends JpaRepository<Factura, Long> {

    @Query("SELECT f FROM Factura f JOIN FETCH f.cliente c JOIN FETCH f.items l JOIN FETCH l.producto WHERE f.id=?1")
    Factura fetchByIdWithClienteWhitItemFacturaWithProducto(Long id);
}
