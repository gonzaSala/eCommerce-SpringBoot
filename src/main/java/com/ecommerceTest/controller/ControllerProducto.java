package com.ecommerceTest.controller;

import com.ecommerceTest.model.Producto;
import com.ecommerceTest.model.Usuario;
import com.ecommerceTest.service.ServiceProducto;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Collections;
import java.util.Optional;


@Controller
@RequestMapping("/productos")
public class ControllerProducto {

    private final Logger LOGGER = LoggerFactory.getLogger(ControllerProducto.class);
    @Autowired
    private ServiceProducto prodService;
    @GetMapping("")
    public String show(Model model) {
        model.addAttribute("productos",prodService.findAll());
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
        prodService.save(producto);
        return "redirect:/productos";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable int id, Model model){
        Producto prod = new Producto();
        Optional<Producto> optionalProducto = prodService.get(id);
        prod=optionalProducto.get();

        model.addAttribute("prod", prod);

        LOGGER.info("Producto buscado: {}", prod);

        return "productos/edit";
    }

    @PostMapping ("/update")
    public String update(Producto prod){
        prodService.update(prod);
        return "redirect:/productos";
    }
@GetMapping("/delete/{id}")
    public String delete(@PathVariable int id){
        prodService.delete(id);

        return "redirect:/productos";
    }
}
