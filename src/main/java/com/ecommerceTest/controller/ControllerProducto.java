package com.ecommerceTest.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/productos")
public class ControllerProducto {

    @GetMapping("")
    public String show() {
        return "productos/show";
    }

    @GetMapping("/create")
    public String create(){
        return "productos/create";
    }
}