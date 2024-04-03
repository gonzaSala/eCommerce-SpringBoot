package com.ecommerceTest.controller;

import com.ecommerceTest.model.DetalleOrden;
import com.ecommerceTest.model.Orden;
import com.ecommerceTest.model.Producto;
import com.ecommerceTest.service.ServiceProducto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/")
public class ControllerHome {

    private final Logger log = LoggerFactory.getLogger(ControllerHome.class);
    @Autowired
    private ServiceProducto serviceProducto;

    List<DetalleOrden> detalles = new ArrayList<DetalleOrden>();
    Orden orden = new Orden();


    @GetMapping("")
    public String home(Model model) {

        model.addAttribute("productos", serviceProducto.findAll());
        return "usuario/home";
    }

    @GetMapping("producto_home/{id}")
    public String productoHome(@PathVariable int id, Model model) {
        log.info("Id enviado como parámetro: {}", id);

        Producto producto = new Producto();
        Optional<Producto> productoOptional = serviceProducto.get(id);
        producto = productoOptional.get();

        model.addAttribute("producto", producto);
        return "usuario/producto_home";
    }

    @PostMapping("/cart")
    public String addCart(@RequestParam int id , @RequestParam int cantidad, Model model) {

        DetalleOrden detalleOrden = new DetalleOrden();
        Producto producto = new Producto();
        double sumaTotal = 0;

        Optional<Producto> optionalProducto = serviceProducto.get(id);
        log.info("producto añanididdo: {}" , optionalProducto.get());
        log.info("Cantidad: {}", cantidad );
        log.info("ID: {}", id );

        producto=optionalProducto.get();

        detalleOrden.setCantidad(cantidad);
        detalleOrden.setPrecio(producto.getPrecio());
        detalleOrden.setNombre(producto.getNombre());
        detalleOrden.setTotal(producto.getPrecio()*cantidad);
        detalleOrden.setProducto(producto);

        detalles.add(detalleOrden);

        sumaTotal=detalles.stream().mapToDouble(dt->dt.getTotal()).sum();

        orden.setTotal(sumaTotal);

        model.addAttribute("cart", detalles);
        model.addAttribute("orden", orden);

        return "usuario/carrito";
    }

}
