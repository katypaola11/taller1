package com.itsqmet.controlador;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class indexController {
    @GetMapping ("/")
    public String mostrarIndex(){
        return "index";
    }
}
