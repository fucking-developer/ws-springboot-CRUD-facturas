package com.jonhdevelop.springbootapifacturas.controllers;

import com.jonhdevelop.springbootapifacturas.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class JwtContoller {

    @Autowired
    private ClienteService clienteService;

    @Secured("ROLE_ADMIN")
    @GetMapping("/clientes/listar")
    public String findAll(){
        return "Hola mundo";
    }

}
