package com.ecommerceTest.repository;

import com.ecommerceTest.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepositoryProducto extends JpaRepository<Producto,Integer> {
}
