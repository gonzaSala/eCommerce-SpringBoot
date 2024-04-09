package com.ecommerceTest.controller;

import com.ecommerceTest.model.Orden;
import com.ecommerceTest.model.Usuario;
import com.ecommerceTest.repository.RepositoryUsuario;
import com.ecommerceTest.service.ServiceOrden;
import com.ecommerceTest.service.ServiceUsuario;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/usuario")
public class ControllerUsuario {
    private final Logger log = LoggerFactory.getLogger(ControllerUsuario.class);



    @Autowired
    private ServiceUsuario serviceUsuario;

    @Autowired
    private ServiceOrden serviceOrden;

    @Autowired
    private RepositoryUsuario repositoryUsuario;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/registro")
    public String create(){
        return "usuario/registro";
    }

    @PostMapping("/save")
    public String save(Usuario usuario){
        log.info("Usuario registro: {}", usuario);

        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        usuario.setTipo("USER");
        repositoryUsuario.save(usuario);

        return "redirect:/";
    }

    @GetMapping("/login")
    public String login(){

        return "usuario/login";
    }

    @GetMapping ("/acceder")
    public String acceder(Usuario usuario, HttpSession session){
        log.info("Accesos: {}",usuario);

        Optional<Usuario> user= serviceUsuario.findById(Integer.parseInt(session.getAttribute("IdUsuario").toString()));
        //log.info("Usuario de db: {}",user.get());

        if (user.isPresent()) {
            session.setAttribute("IdUsuario",user.get().getId());
            if(user.get().getTipo().equals("ADMIN")){
                return "redirect:/administrador";
            }else if(user.get().getTipo().equals("USER")){
                return "redirect:/";
            }
        }else{
            log.info("Usuario no existe!!!!");
        }


        return "redirect:/";
    }

    @GetMapping("/compras")
    public String obtenerCompras(Model model, HttpSession session){
        model.addAttribute("sesion", session.getAttribute("IdUsuario"));

        Usuario usuario = serviceUsuario.findById(Integer.parseInt(session.getAttribute("IdUsuario").toString())).get();

        List<Orden> ordenes = serviceOrden.findByUsuario(usuario);

        model.addAttribute("ordenes",ordenes);

        return "/usuario/compras";
    }

    @GetMapping("/detalle/{id}")
    public String detalleCompra(@PathVariable Integer id, HttpSession session, Model model){
        log.info("Id de la orden: {}",id);
        Optional<Orden> orden = serviceOrden.findById(id);

        model.addAttribute("detalles", orden.get().getDetalle());

        model.addAttribute("sesion", session.getAttribute("IdUsuario"));

        return "usuario/detallecompra";
    }

    @GetMapping("/cerrar")
    public String cerrarSesion(HttpSession session){

        session.removeAttribute("IdUsuario");

        return  "redirect:/";
    }
}
