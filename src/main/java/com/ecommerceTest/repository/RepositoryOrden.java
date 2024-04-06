package com.ecommerceTest.repository;

import com.ecommerceTest.model.Orden;
import com.ecommerceTest.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RepositoryOrden extends JpaRepository<Orden, Integer> {
    List<Orden> findByUsuario (Usuario usuario);

}
