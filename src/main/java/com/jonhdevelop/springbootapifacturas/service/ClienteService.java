package com.jonhdevelop.springbootapifacturas.service;

import java.util.List;

import com.jonhdevelop.springbootapifacturas.entity.Cliente;
import com.jonhdevelop.springbootapifacturas.entity.Factura;
import com.jonhdevelop.springbootapifacturas.entity.Producto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ClienteService {
	
	List<Cliente> findAll();
	
	Page<Cliente> findAll(Pageable pageable);

	void save(Cliente cliente);
	
	Cliente findOne(Long id);
	
	void delete(Long id);

	List<Producto> findByNombre(String term);
	void saveFactura(Factura factura);
	Producto findProductoById(Long id);

	Factura findFacturaById(Long id);

	void deleteFactura(Long id);

	Factura fetchFacturaById(Long id);

	Cliente fetchByIdWithFacturas(Long id);

}
