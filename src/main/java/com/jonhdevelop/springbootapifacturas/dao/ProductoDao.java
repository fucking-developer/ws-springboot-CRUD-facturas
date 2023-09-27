package com.jonhdevelop.springbootapifacturas.dao;

import com.jonhdevelop.springbootapifacturas.entity.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductoDao extends JpaRepository<Producto, Long> {

    @Query("SELECT p FROM Producto p WHERE p.nombre LIKE %?1%")
    public List<Producto> findByNombre(String term);

    public  List<Producto> findByNombreLikeIgnoreCase(String term);
}
