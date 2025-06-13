package com.itsqmet.entidad;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

@Entity
@Data

public class Autor {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String nacionalidad;

    @DateTimeFormat (pattern = "yyyy-mm-dd")
    private Date fechaNacimiento;

    //Cardanilidad
    @OneToMany (mappedBy = "autor", fetch = FetchType.LAZY) //Esto quiere decir traer los datos
    private List<Libro> libros;
}
