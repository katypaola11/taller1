package com.itsqmet.repositorio;

import com.itsqmet.entidad.Rol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RolRepositorio extends JpaRepository <Rol,Long> {
}
