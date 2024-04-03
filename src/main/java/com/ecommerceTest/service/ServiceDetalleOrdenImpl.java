package com.ecommerceTest.service;

import com.ecommerceTest.model.DetalleOrden;
import com.ecommerceTest.repository.RepositoryDetalleOrden;
import org.springframework.stereotype.Service;

@Service
public class ServiceDetalleOrdenImpl implements ServiceDetalleOrden{

    private RepositoryDetalleOrden repositoryDetalleOrden;
    @Override
    public DetalleOrden save(DetalleOrden detalleOrden) {
        return repositoryDetalleOrden.save(detalleOrden);
    }
}
