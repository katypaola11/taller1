package com.itsqmet.repositorio;

import com.itsqmet.entidad.Libro;
import com.itsqmet.entidad.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface LibroRepositorio extends JpaRepository <Libro, Long> {

    List<Libro> findByTituloContainingIgnoreCase(String titulo);
    List<Libro> findByAutorId(Long autor_id);
}
