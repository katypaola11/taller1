package com.itsqmet.entidad;

import jakarta.persistence.*;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Entity
@Data
public class Libro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column (unique = true) //Reglas de entidad no es lo mismo que vi anteriormente esto es directo en la base de datos
    private String titulo;
    private String Editorial;

    @DateTimeFormat (pattern = "yyyy-mm-dd")
    private Date publicacion;

    private int paginas;
    private String idioma;

    @ManyToOne
    @JoinColumn(name = "codigo_autor")
    private Autor autor;




}
