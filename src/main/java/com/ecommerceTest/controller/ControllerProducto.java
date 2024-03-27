package com.ecommerceTest.controller;

import com.ecommerceTest.model.Producto;
import com.ecommerceTest.model.Usuario;
import com.ecommerceTest.service.ServiceProducto;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Collections;


@Controller
@RequestMapping("/productos")
public class ControllerProducto {

    private final Logger LOGGER = LoggerFactory.getLogger(ControllerProducto.class);
    @Autowired
    private ServiceProducto pordService;
    @GetMapping("")
    public String show() {
        return "productos/show";
    }

    @GetMapping("/create")
    public String create(){
        return "productos/create";
    }

    @PostMapping("/save")
    public String save(Producto producto){
        LOGGER.info("este es el objeto producto {}", producto);

        Usuario u =new Usuario(1,"","","","","","", Collections.emptyList());
        producto.setUsuario(u);
        pordService.save(producto);
        return "redirect:/productos";
    }
}
