package com.itsqmet.entidad;

import jakarta.persistence.*;

import java.util.List;


@Entity

public class Rol {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String  nombre;


    @OneToMany(mappedBy = "rol")
    private List<Usuario> usuarios;


}
