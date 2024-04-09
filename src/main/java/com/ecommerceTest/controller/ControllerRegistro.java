package com.ecommerceTest.controller;

import com.ecommerceTest.model.Usuario;
import com.ecommerceTest.repository.RepositoryUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ControllerRegistro {
    @Autowired
    private RepositoryUsuario repositoryUsuario;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/registro/usuario")
    public Usuario crear(@RequestBody Usuario usuario){
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        return repositoryUsuario.save(usuario);
    }
}
