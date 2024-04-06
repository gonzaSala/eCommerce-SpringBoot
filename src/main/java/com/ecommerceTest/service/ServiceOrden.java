package com.ecommerceTest.service;

import com.ecommerceTest.model.Orden;
import com.ecommerceTest.model.Usuario;

import java.util.List;
import java.util.Optional;

public interface ServiceOrden {
    List<Orden> findAll();
    Orden save (Orden orden);
    String generarNumOrden();
    Optional<Orden> findById(Integer id);
    List<Orden> findByUsuario (Usuario usuario);

}
