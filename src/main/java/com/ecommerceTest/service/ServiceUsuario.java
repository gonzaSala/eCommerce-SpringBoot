package com.ecommerceTest.service;

import com.ecommerceTest.model.Usuario;

import javax.swing.text.html.Option;
import java.util.Optional;

public interface ServiceUsuario {

    Optional<Usuario> findById(int id);
    Usuario save (Usuario usuario);
}
