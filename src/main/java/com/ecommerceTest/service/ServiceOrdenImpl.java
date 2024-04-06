package com.ecommerceTest.service;

import com.ecommerceTest.model.Orden;
import com.ecommerceTest.model.Usuario;
import com.ecommerceTest.repository.RepositoryOrden;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ServiceOrdenImpl implements ServiceOrden {

    @Autowired
    private RepositoryOrden ordenRepository;

    @Override
    public Orden save(Orden orden) {
        return ordenRepository.save(orden);
    }



    @Override
    public List<Orden> findAll() {
        return ordenRepository.findAll();
    }
    // 0000010
    public String generarNumOrden() {
        synchronized (this) { // Bloquea el método para garantizar exclusividad durante la generación del número de orden
            int numero = 0;
            String numeroConcatenado = "";

            List<Orden> ordenes = findAll();
            Optional<Integer> maxNumeroOpt = ordenes.stream().map(Orden::getNumero)
                    .map(Integer::parseInt)
                    .max(Integer::compare);

            if (maxNumeroOpt.isPresent()) {
                numero = maxNumeroOpt.get() + 1;
            } else {
                numero = 1;
            }

            numeroConcatenado = String.format("%010d", numero); // Formato de 10 dígitos con ceros a la izquierda
            return numeroConcatenado;
        }
    }

    @Override
    public List<Orden> findByUsuario(Usuario usuario) {
        return ordenRepository.findByUsuario(usuario);
    }

    @Override
    public Optional<Orden> findById(Integer id) {
        return ordenRepository.findById(id);
    }

}