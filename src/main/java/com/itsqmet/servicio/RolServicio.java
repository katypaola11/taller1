package com.itsqmet.servicio;

import com.itsqmet.entidad.Rol;
import com.itsqmet.repositorio.RolRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RolServicio {
    @Autowired
    private RolRepositorio rolRepositorio;

    public List<Rol> mostrarRoles(){
        return rolRepositorio.findAll();
    }
}
