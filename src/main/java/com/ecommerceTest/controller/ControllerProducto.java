package com.ecommerceTest.controller;

import com.ecommerceTest.model.Producto;
import org.slf4j.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/productos")
public class ControllerProducto {

    private final Logger LOGGER = LoggerFactory.getLogger(ControllerProducto.class);
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
        return "redirect:/productos";
    }
}
