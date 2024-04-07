package com.ecommerceTest.service;

import com.ecommerceTest.model.Usuario;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

public interface ServiceUsuario {

    Optional<Usuario> findById(Integer id);
    Usuario save (Usuario usuario);
    Optional<Usuario> findByEmail(String email);

    List<Usuario> findAll();
}
