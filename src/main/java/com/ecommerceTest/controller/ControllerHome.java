package com.ecommerceTest.controller;

import com.ecommerceTest.model.DetalleOrden;
import com.ecommerceTest.model.Orden;
import com.ecommerceTest.model.Producto;
import com.ecommerceTest.model.Usuario;
import com.ecommerceTest.service.ServiceDetalleOrden;
import com.ecommerceTest.service.ServiceOrden;
import com.ecommerceTest.service.ServiceProducto;
import com.ecommerceTest.service.ServiceUsuario;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/")
public class ControllerHome {

    private final Logger log = LoggerFactory.getLogger(ControllerHome.class);
    @Autowired
    private ServiceProducto serviceProducto;
    @Autowired
    private ServiceUsuario serviceUsuario;

    @Autowired
    private ServiceOrden serviceOrden;
    @Autowired
    private ServiceDetalleOrden serviceDetalleOrden;

    List<DetalleOrden> detalles = new ArrayList<DetalleOrden>();
    Orden orden = new Orden();


    @GetMapping("/")
    public String home(Model model, HttpSession session) {
        Integer idUsuario = (Integer) session.getAttribute("IdUsuario");
        String tipoUsuario = null;


        log.info("Session del user: {}", session.getAttribute("IdUsuario"));
        if (idUsuario != null) {
            tipoUsuario = obtenerTipoUsuario(idUsuario);
        }
        log.info("Tipo de usuario: {}", tipoUsuario);

        model.addAttribute("productos", serviceProducto.findAll());
        model.addAttribute("tipoUsuario", tipoUsuario);
        model.addAttribute("sesion", session.getAttribute("IdUsuario"));

        return "usuario/home";
    }

    @GetMapping("producto_home/{id}")
    public String productoHome(@PathVariable int id, Model model, HttpSession session) {
        log.info("Id enviado como parámetro: {}", id);

        Integer userId = (Integer) session.getAttribute("IdUsuario");
        if (userId != null) {
            Optional<Usuario> userOptional = serviceUsuario.findById(userId);
            if (userOptional.isPresent()) {
                Usuario usuario = userOptional.get();
                String tipoUsuario = usuario.getTipo();

                model.addAttribute("tipoUsuario", tipoUsuario);
            }
        }

        Optional<Producto> productoOptional = serviceProducto.get(id);
        Producto producto = productoOptional.orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        model.addAttribute("producto", producto);

        if (userId != null) {
            model.addAttribute("sesion", userId);
        }

        return "usuario/producto_home";
    }

    @PostMapping("/cart")
    public String addCart(@RequestParam Integer id, @RequestParam Integer cantidad, Model model,HttpSession session) {
        DetalleOrden detalleOrden = new DetalleOrden();
        Producto producto = new Producto();
        double sumaTotal = 0;


        Optional<Producto> optionalProducto = serviceProducto.get(id);
        log.info("Producto añadido: {}", optionalProducto.get());
        log.info("Cantidad: {}", cantidad);
        producto = optionalProducto.get();

        detalleOrden.setCantidad(cantidad);
        detalleOrden.setPrecio(producto.getPrecio());
        detalleOrden.setNombre(producto.getNombre());
        detalleOrden.setTotal(producto.getPrecio() * cantidad);
        detalleOrden.setProducto(producto);

        Integer idProducto = producto.getId();
        boolean ingresado = detalles.stream().anyMatch(p -> p.getProducto().getId() == idProducto);

        if (!ingresado) {
            detalles.add(detalleOrden);
        }

        sumaTotal = detalles.stream().mapToDouble(dt -> dt.getTotal()).sum();

        orden.setTotal(sumaTotal);
        model.addAttribute("cart", detalles);
        model.addAttribute("orden", orden);
        model.addAttribute("sesion", session.getAttribute("IdUsuario"));


        return "usuario/carrito";
    }

    @GetMapping("/delete/cart/{id}")
    public String deleteProdCart(@PathVariable int id, Model model) {

        List<DetalleOrden> ordenesNuevas = new ArrayList<DetalleOrden>();

        for (DetalleOrden detalleOrden : detalles) {
            if (detalleOrden.getProducto().getId() != id) {
                ordenesNuevas.add(detalleOrden);
            }
        }

        detalles = ordenesNuevas;

        double sumaTotal = 0;
        sumaTotal = detalles.stream().mapToDouble(dt -> dt.getTotal()).sum();

        orden.setTotal(sumaTotal);

        model.addAttribute("cart", detalles);
        model.addAttribute("orden", orden);


        return "usuario/carrito";
    }

    @GetMapping("/getCart")
    public String getCart(Model model, HttpSession session) {

        model.addAttribute("cart", detalles);
        model.addAttribute("orden", orden);


        model.addAttribute("sesion", session.getAttribute("IdUsuario"));
        return "/usuario/carrito";
    }


    @GetMapping("/order")
    public String order(Model model, HttpSession session) {

        Usuario usuario = serviceUsuario.findById(Integer.parseInt(session.getAttribute("IdUsuario").toString())).get();


        model.addAttribute("cart", detalles);
        model.addAttribute("orden", orden);
        model.addAttribute("usuario", usuario);

        return "usuario/resumen_orden";
    }

    @GetMapping("/saveOrder")
    public String saveOrder(HttpSession session) {

        Date fechaCreacion = new Date();
        orden.setFechaCreacion(fechaCreacion);
        orden.setNumero(serviceOrden.generarNumOrden());

        Usuario usuario = serviceUsuario.findById(Integer.parseInt(session.getAttribute("IdUsuario").toString())).get();

        orden.setUsuario(usuario);
        serviceOrden.save(orden);

        for (DetalleOrden dt : detalles) {

            dt.setOrden(orden);
            serviceDetalleOrden.save(dt);

        }

        //clear list
        orden = new Orden();
        detalles.clear();

        return "redirect:/";
    }

    @PostMapping("/search")
    public String searchProduct(@RequestParam String nombre, Model model, HttpSession session) {
        Integer idUsuario = (Integer) session.getAttribute("IdUsuario");
        String tipoUsuario = null;
        if (idUsuario != null) {
            tipoUsuario = obtenerTipoUsuario(idUsuario);
        }

        log.info("nombre del producto: {}", nombre);
        String nombreMinuscula = nombre.toLowerCase();
        List<Producto> productos = serviceProducto.findAll().stream().filter(p -> p.getNombre().toLowerCase().contains(nombreMinuscula)).collect(Collectors.toList());

        model.addAttribute("tipoUsuario", tipoUsuario);
        model.addAttribute("sesion", session.getAttribute("IdUsuario"));
        model.addAttribute("productos", productos);


        return "usuario/home";
    }

    private String obtenerTipoUsuario(Integer idUsuario) {
        Usuario usuario = serviceUsuario.findById(idUsuario).orElse(null);

        if (usuario != null) {
            return usuario.getTipo();
        } else {
            return null;
        }
    }

}
