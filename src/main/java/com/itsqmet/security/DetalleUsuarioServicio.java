package com.itsqmet.security;

import com.itsqmet.entidad.Usuario;
import com.itsqmet.repositorio.UsuarioRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DetalleUsuarioServicio implements UserDetailsService {

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;


    @Override
    public UserDetails loadUserByUsername(String nombreCompleto) throws UsernameNotFoundException {
        List<Usuario> usuarios = usuarioRepositorio.findByNombreCompletoContainingIgnoreCase(nombreCompleto);

        if (usuarios.isEmpty()) {
            throw new UsernameNotFoundException("Usuario no encontrado");
        }

        Usuario usuario = usuarios.get(0); // Tomar el primer usuario encontrado

        return User.builder()
                .username(usuario.getEmail())
                .password(usuario.getPassword())
                .build();
    }
}
