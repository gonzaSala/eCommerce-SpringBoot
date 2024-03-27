package com.ecommerceTest.service;

import com.ecommerceTest.model.Producto;
import com.ecommerceTest.repository.RepositoryProducto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ServiceProductoImpl implements ServiceProducto{

    @Autowired
    private RepositoryProducto repoProd;

    @Override
    public Producto save(Producto producto) {
        return repoProd.save(producto);
    }

    @Override
    public Optional<Producto> get(int id) {
        return repoProd.findById(id);
    }

    @Override
    public void update(Producto producto) {
        repoProd.save(producto);
    }

    @Override
    public void delete(int id) {
        repoProd.deleteById(id);
    }

    @Override
    public List<Producto> findAll() {
        return repoProd.findAll();
    }
}
