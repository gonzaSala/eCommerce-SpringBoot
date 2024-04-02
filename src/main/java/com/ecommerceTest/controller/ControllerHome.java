package com.ecommerceTest.controller;

import com.ecommerceTest.service.ServiceProducto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class ControllerHome {

    @Autowired
    private ServiceProducto serviceProducto;


    @GetMapping("")
    public String home(Model model){

        model.addAttribute("productos", serviceProducto.findAll());
        return "usuario/home";
    }

}
