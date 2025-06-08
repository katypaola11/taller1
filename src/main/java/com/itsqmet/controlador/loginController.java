package com.itsqmet.controlador;

import com.itsqmet.entidad.Usuario;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class loginController {

    @GetMapping("/login")
    public String mostrarLogin(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "pages/login";
    }

    @PostMapping("/logC")
    public String enviarCatalogo(@ModelAttribute Usuario usuario, Model model) {

        return "pages/catalogo";
    }

}
