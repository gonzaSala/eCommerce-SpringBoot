package com.ecommerceTest.controller;

import com.ecommerceTest.model.Orden;
import com.ecommerceTest.model.Producto;
import com.ecommerceTest.service.ServiceDetalleOrden;
import com.ecommerceTest.service.ServiceOrden;
import com.ecommerceTest.service.ServiceProducto;
import com.ecommerceTest.service.ServiceUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/administrador")
public class ControllerAdmin {

    @Autowired
    private ServiceProducto serviceProducto;

    @Autowired
    private ServiceUsuario serviceUsuario;

    @Autowired
    private ServiceOrden serviceOrden;

    @Autowired
    private ServiceDetalleOrden serviceDetalleOrden;

    @GetMapping("")
    public String home(Model model) {
        List<Producto> productos = serviceProducto.findAll();
        model.addAttribute("productos", productos);
        return "administrador/home";
    }

    @GetMapping("/usuarios")
    public String usuario(Model model) {
        model.addAttribute("usuarios", serviceUsuario.findAll());
        return "administrador/usuarios";
    }

    @GetMapping("/ordenes")
    public String ordenes(Model model) {


        model.addAttribute("ordenes", serviceOrden.findAll());

        return "administrador/ordenes";
    }

    @GetMapping("/detalles/{id}")
    public String detallesOrdenes(Model model, @PathVariable Integer id) {

        Orden orden = serviceOrden.findById(id).get();
        model.addAttribute("detalles", orden.getDetalle());

        return "/administrador/detalleorden";
    }


}
