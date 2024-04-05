package com.ecommerceTest.service;

import com.ecommerceTest.model.DetalleOrden;
import com.ecommerceTest.repository.RepositoryDetalleOrden;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServiceDetalleOrdenImpl implements ServiceDetalleOrden{

    @Autowired
    private RepositoryDetalleOrden repositoryDetalleOrden;
    @Override
    public DetalleOrden save(DetalleOrden detalleOrden) {
        return repositoryDetalleOrden.save(detalleOrden);
    }
}
