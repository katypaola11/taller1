package com.itsqmet.servicio;


import com.itsqmet.entidad.Usuario;
import com.itsqmet.repositorio.UsuarioRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class UsuarioServicio {

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    public List<Usuario> obtenerLista(){
        return usuarioRepositorio.findAll();
    }

    public void  guardarUsuario(Usuario usuario){
        System.out.println("Guardado en mysql:" + usuario);
        usuarioRepositorio.save(usuario);
    }

    public Optional<Usuario> buscarporId(Long id){
        return usuarioRepositorio.findById(id);
    }

    public void eliminarUsuario(Long id){
        usuarioRepositorio.deleteById(id);
    }

}
