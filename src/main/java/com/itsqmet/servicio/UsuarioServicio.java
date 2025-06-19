package com.itsqmet.servicio;


import com.itsqmet.entidad.Usuario;
import com.itsqmet.repositorio.UsuarioRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class UsuarioServicio {

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<Usuario> obtenerLista(){
        return usuarioRepositorio.findAll();
    }

    // Guardar usuario
    public Usuario guardarUsuario(Usuario usuario) {
        String passwordEncriptado = passwordEncoder.encode(usuario.getPassword());
        usuario.setPassword(passwordEncriptado);
        return usuarioRepositorio.save(usuario);
    }

    public Optional<Usuario> buscarporId(Long id){
        return usuarioRepositorio.findById(id);
    }

    public void eliminarUsuario(Long id){
        usuarioRepositorio.deleteById(id);
    }

}
