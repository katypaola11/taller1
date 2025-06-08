package com.itsqmet.controlador;

import com.itsqmet.entidad.Usuario;
import com.itsqmet.servicio.UsuarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Optional;

@Controller
public class usuarioController {


    @Autowired
    private UsuarioServicio servicio;

    //registrar Usuario
    @GetMapping("/registro")
    public String registroUsuario(Model model){
        model.addAttribute("usuario", new Usuario());
        return "pages/registroUsuario";
    }

    //visualizar usuario
    @GetMapping("/usuarioN")
    public String mostrarUsuario(Model model){
        List<Usuario> usuarios = servicio.obtenerLista();
        model.addAttribute("usuarios", usuarios);
        return "pages/lista";
    }

    //guardar
    @PostMapping("/guardarUsuario")
    public String nuevoUsuario(@ModelAttribute Usuario usuario){
        System.out.println("Guardar usuario:" + usuario);
        servicio.guardarUsuario(usuario);
        return "redirect:/usuarioN";
    }

    //actualizar - MOSTRAR FORMULARIO
    @GetMapping("/actualizar/{id}")
    public String actualizarUsuario(@PathVariable Long id, Model model) {
        Optional<Usuario> usuarioOpt = servicio.buscarporId(id);
        if (usuarioOpt.isPresent()) {
            model.addAttribute("usuario", usuarioOpt.get()); // ✅ CORREGIDO: era "menu"
            return "pages/registroUsuario";
        } else {
            // Manejar caso cuando no se encuentra el usuario
            return "redirect:/usuarioN";
        }
    }

    //actualizar - PROCESAR FORMULARIO
    @PostMapping("/actualizarUsuario")
    public String procesarActualizacion(@ModelAttribute Usuario usuario) {
        System.out.println("Actualizar usuario:" + usuario);
        servicio.guardarUsuario(usuario); // Mismo método save funciona para crear y actualizar
        return "redirect:/usuarioN";
    }

    //eliminar
    @GetMapping("/eliminar/{id}")
    public String eliminarUsuario(@PathVariable Long id) {
        servicio.eliminarUsuario(id);
        return "redirect:/usuarioN";
    }

}
