package com.ecommerceTest.service;

import com.ecommerceTest.model.Orden;

import java.util.List;

public interface ServiceOrden {
    List<Orden> findAll();
    Orden save (Orden orden);
}
