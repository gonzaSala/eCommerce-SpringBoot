package com.ecommerceTest.service;

import com.ecommerceTest.model.Orden;
import com.ecommerceTest.repository.RepositoryOrden;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ServiceOrdenImpl implements ServiceOrden {

    @Autowired
    private RepositoryOrden repositoryOrden;

    @Override
    public List<Orden> findAll() {
        return repositoryOrden.findAll();
    }

    @Override
    public Orden save(Orden orden) {
        return repositoryOrden.save(orden);
    }

    public String generarNumOrden(){

        int numero = 0;
        String numeroConcatenado = "";

        List<Orden> ordenes = findAll();
        List<Integer> numeros = new ArrayList<>();

        ordenes.stream().forEach(o -> numeros.add(Integer.parseInt(o.getNumero())));

        if(ordenes.isEmpty()){
            numero = 1;
        }else{
            numero = numeros.stream().max(Integer::compare).get();
            numero++;
        }

        if(numero<10){
            numeroConcatenado="000000000"+String.valueOf(numero);
        }else if(numero<100){
            numeroConcatenado="00000000"+String.valueOf(numero);
        }else if (numero<1000){
            numeroConcatenado="0000000"+String.valueOf(numero);
        }else if(numero<10000){
            numeroConcatenado="000000"+String.valueOf(numero);

        }

        return numeroConcatenado;
    }
}
