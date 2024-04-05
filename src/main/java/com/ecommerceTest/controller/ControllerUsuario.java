package com.ecommerceTest.controller;

import com.ecommerceTest.model.Usuario;
import com.ecommerceTest.service.ServiceUsuario;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/usuario")
public class ControllerUsuario {
    private final Logger log = LoggerFactory.getLogger(ControllerUsuario.class);

    @Autowired
    private ServiceUsuario serviceUsuario;

    @GetMapping("/registro")
    public String create(){
        return "usuario/registro";
    }

    @PostMapping("/save")
    public String save(Usuario usuario){
        log.info("Usuario registro: {}", usuario);

        usuario.setTipo("USER");
        serviceUsuario.save(usuario);

        return "redirect:/";
    }
}
