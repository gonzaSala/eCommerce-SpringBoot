package com.ecommerceTest.repository;

import com.ecommerceTest.model.DetalleOrden;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepositoryDetalleOrden extends JpaRepository<DetalleOrden,Integer> {



}
