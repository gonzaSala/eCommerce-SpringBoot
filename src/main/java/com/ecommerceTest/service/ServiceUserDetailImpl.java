package com.ecommerceTest.service;

import com.ecommerceTest.model.Usuario;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ServiceUserDetailImpl implements UserDetailsService {
    @Autowired
    private ServiceUsuario serviceUsuario;

    @Autowired
    HttpSession session;

    private Logger log =LoggerFactory.getLogger(ServiceUserDetailImpl.class);

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    log.info("Esto es el username: {}", username);
        Optional<Usuario> optionalUsuario = serviceUsuario.findByEmail(username);

        if(optionalUsuario.isPresent()){
            log.info("Este es el ID del usuario: {}", optionalUsuario.get().getId());
            session.setAttribute("IdUsuario", optionalUsuario.get().getId());
            Usuario usuario = optionalUsuario.get();

            return User.builder().username(usuario.getNombre()).password(usuario.getPassword()).roles(usuario.getTipo()).build();

        }else{
            throw new UsernameNotFoundException("Usuario no encontraado");
        }
    }
}
