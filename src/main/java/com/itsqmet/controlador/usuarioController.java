package com.itsqmet.controlador;

import com.itsqmet.entidad.Rol;
import com.itsqmet.entidad.Usuario;
import com.itsqmet.servicio.RolServicio;
import com.itsqmet.servicio.UsuarioServicio;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Optional;

@Controller
public class
usuarioController {


    @Autowired
    private UsuarioServicio servicio;
    @Autowired
    private RolServicio rolServicio;

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
        List<Rol> roles=rolServicio.mostrarRoles();
        model.addAttribute("roles",roles);
        return "pages/lista";
    }



    //guardar
    @PostMapping("/guardarUsuario")
    public String nuevoUsuario(@Valid @ModelAttribute Usuario usuario, BindingResult bindingResult, Model model){
        if (bindingResult.hasErrors()){
            model.addAttribute("errores",bindingResult.getAllErrors());
            return "pages/registroUsuario";
        }else {
            System.out.println("Guardar usuario:" + usuario);
            servicio.guardarUsuario(usuario);
            return "redirect:/usuarioN";
        }
    }

    //actualizar - MOSTRAR FORMULARIO
    @GetMapping("/actualizar/{id}")
    public String actualizarUsuario(@PathVariable Long id, Model model) {
        Optional<Usuario> usuarioOpt = servicio.buscarporId(id);
        List<Rol> roles=rolServicio.mostrarRoles();
        model.addAttribute("roles",roles);
        if (usuarioOpt.isPresent()) {
            model.addAttribute("usuario", usuarioOpt.get());
            return "pages/registroUsuario";
        } else {

            return "redirect:/usuarioN";
        }
    }

    //actualizar - PROCESAR FORMULARIO
    @PostMapping("/actualizarUsuario")
    public String procesarActualizacion(@ModelAttribute Usuario usuario) {
        System.out.println("Actualizar usuario:" + usuario);
        servicio.guardarUsuario(usuario);
        return "redirect:/usuarioN";
    }

    //eliminar
    @GetMapping("/eliminar/{id}")
    public String eliminarUsuario(@PathVariable Long id) {
        servicio.eliminarUsuario(id);
        return "redirect:/usuarioN";
    }

}
