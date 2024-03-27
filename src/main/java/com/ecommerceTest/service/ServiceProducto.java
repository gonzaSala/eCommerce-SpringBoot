package com.ecommerceTest.service;

import com.ecommerceTest.model.Producto;

import java.util.Optional;

public interface ServiceProducto {

    public Producto save(Producto producto);
    public Optional<Producto> get(int id);
    public void update(Producto producto);
    public void delete(int id);
}
