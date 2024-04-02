package com.ecommerceTest.controller;

import com.ecommerceTest.model.Producto;
import com.ecommerceTest.service.ServiceProducto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
@RequestMapping("/")
public class ControllerHome {

    private final Logger log = LoggerFactory.getLogger(ControllerHome.class);
    @Autowired
    private ServiceProducto serviceProducto;


    @GetMapping("")
    public String home(Model model){

        model.addAttribute("productos", serviceProducto.findAll());
        return "usuario/home";
    }

    @GetMapping("producto_home/{id}")
    public String productoHome(@PathVariable int id, Model model){
        log.info("Id enviado como parámetro: {}", id);

        Producto producto = new Producto();
        Optional<Producto> productoOptional = serviceProducto.get(id);
        producto = productoOptional.get();

        model.addAttribute("producto",producto);
        return "usuario/producto_home";
    }

}
