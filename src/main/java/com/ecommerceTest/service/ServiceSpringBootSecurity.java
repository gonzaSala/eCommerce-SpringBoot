package com.ecommerceTest.service;

import com.ecommerceTest.AuthenticationSuccesHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractAuthenticationFilterConfigurer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
public class ServiceSpringBootSecurity {
    @Autowired
    private ServiceUserDetailImpl serviceUserDetail;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(registry -> {
                    registry.requestMatchers("/usuario/registro/**").permitAll();
                    registry.requestMatchers("/usuario/producto_home/").permitAll();
                    registry.requestMatchers("/").permitAll();
                   // registry.requestMatchers("/**").hasRole("ADMIN");
                    registry.requestMatchers("/administrador/**").hasRole("ADMIN");
                    registry.requestMatchers("/productos/**").hasRole("ADMIN");
                    registry.requestMatchers("/order/**").hasAnyRole("USER","ADMIN");
                    registry.anyRequest().authenticated();
                })
                .formLogin(httpSecurityFormLoginConfigurer -> {
                    httpSecurityFormLoginConfigurer.loginPage("/usuario/login")
                            .successHandler(new AuthenticationSuccesHandler())
                            .permitAll()
                            .defaultSuccessUrl("/usuario/acceder")
                    ;
                })
                .build();
    }

//    @Bean
//    public UserDetailsService userDetailsService(){
//        UserDetails normalUser= User.builder()
//                .username("gc")
//                .password("1234")
//                .roles("USER")
//                .build();
//        UserDetails adminUser= User.builder()
//                .username("admin")
//                .password("1234")
//                .roles("ADMIN","USER")
//                .build();
//        return new InMemoryUserDetailsManager(normalUser,adminUser);
//    }

    @Bean
    public UserDetailsService userDetailsService() {
        return serviceUserDetail;
    }

    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService());
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
