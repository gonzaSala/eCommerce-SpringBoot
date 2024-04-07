package com.ecommerceTest.controller;

import com.ecommerceTest.model.Producto;
import com.ecommerceTest.service.ServiceProducto;
import com.ecommerceTest.service.ServiceUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/administrador")
public class ControllerAdmin {

    @Autowired
    private ServiceProducto serviceProducto;

    @Autowired
    private ServiceUsuario serviceUsuario;

    @GetMapping("")
    public String home(Model model) {
        List<Producto> productos = serviceProducto.findAll();
        model.addAttribute("productos", productos);
        return "administrador/home";
    }

    @GetMapping("/usuarios")
    public String usuario(Model model){
        model.addAttribute("usuarios",serviceUsuario.findAll());
        return "administrador/usuarios";
    }





}
