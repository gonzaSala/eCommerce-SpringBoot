package com.ecommerceTest.controller;

import com.ecommerceTest.model.Producto;
import com.ecommerceTest.model.Usuario;
import com.ecommerceTest.service.ServiceProducto;
import com.ecommerceTest.service.UploadFileService;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collections;
import java.util.Optional;


@Controller
@RequestMapping("/productos")
public class ControllerProducto {

    private final Logger LOGGER = LoggerFactory.getLogger(ControllerProducto.class);
    @Autowired
    private ServiceProducto prodService;
    @Autowired
    private UploadFileService upload;

    @GetMapping("")
    public String show(Model model) {
        model.addAttribute("productos", prodService.findAll());
        return "productos/show";
    }

    @GetMapping("/create")
    public String create() {
        return "productos/create";
    }

    @PostMapping("/save")
    public String save(Producto producto, @RequestParam("img") MultipartFile file) throws IOException {
        LOGGER.info("este es el objeto producto {}", producto);

        Usuario u = new Usuario(1, "", "", "","", "", "", "", Collections.emptyList());
        producto.setUsuario(u);

        //imagen
        if (producto.getId() == null) { //cuando se crea un producto
            String nombreImg = upload.saveImage(file);
            producto.setImagen(nombreImg);
        } else {

        }
        prodService.save(producto);
        return "redirect:/productos";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable int id, Model model) {
        Producto prod = new Producto();
        Optional<Producto> optionalProducto = prodService.get(id);
        prod = optionalProducto.get();

        model.addAttribute("prod", prod);

        LOGGER.info("Producto buscado: {}", prod);

        return "productos/edit";
    }

    @PostMapping("/update")
    public String update(Producto prod, @RequestParam("img") MultipartFile file) throws IOException {

        Producto p = new Producto();
        p = prodService.get(prod.getId()).get();

        if (file.isEmpty()) { // edicion de prod sin cambiar la img

            prod.setImagen(p.getImagen());
        } else { //cuando se edita tmb la img


            //eliminar cuando no sea la img por default
            if (!p.getImagen().equals("defualt.jpg")) {
                upload.deleteImage(p.getImagen());
            }

            String nombreImg = upload.saveImage(file);
            prod.setImagen(nombreImg);
        }
        prod.setUsuario(p.getUsuario());
        prodService.update(prod);
        return "redirect:/productos";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable int id) {
        Producto p = new Producto();
        p = prodService.get(id).get();

        //eliminar cuando no sea la img por default
        if (!p.getImagen().equals("default.jpg")) {
            upload.deleteImage(p.getImagen());
        }

        prodService.delete(id);

        return "redirect:/productos";
    }
}
