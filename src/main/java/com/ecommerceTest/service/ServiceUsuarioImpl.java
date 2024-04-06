package com.ecommerceTest.service;

import com.ecommerceTest.model.Usuario;
import com.ecommerceTest.repository.RepositoryUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ServiceUsuarioImpl implements ServiceUsuario{

    @Autowired
    private RepositoryUsuario repositoryUsuario;

    @Override
    public Optional<Usuario> findById(int id) {
        return repositoryUsuario.findById(id);
    }

    @Override
    public Usuario save(Usuario usuario) {
        return repositoryUsuario.save(usuario);
    }

    @Override
    public Optional<Usuario> findByEmail(String email) {
        return repositoryUsuario.findByEmail(email);
    }
}
