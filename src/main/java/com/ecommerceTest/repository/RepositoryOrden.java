package com.ecommerceTest.repository;

import com.ecommerceTest.model.Orden;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepositoryOrden extends JpaRepository<Orden, Integer> {


}
