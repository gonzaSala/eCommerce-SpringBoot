package com.ecommerceTest.repository;

import com.ecommerceTest.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RepositoryUsuario extends JpaRepository<Usuario, Integer> {

    Optional<Usuario> findByEmail(String email);

}
