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
    private String editorial;



    private int paginas;
    private String idioma;
    @Column(name = "contador_visualizaciones", columnDefinition = "integer default 0")
    private Integer contadorVisualizaciones = 0;

    @Column(name = "contador_descargas", columnDefinition = "integer default 0")
    private Integer contadorDescargas = 0;

    // Getters y Setters
    public Integer getContadorVisualizaciones() {
        return contadorVisualizaciones != null ? contadorVisualizaciones : 0;
    }

    public void setContadorVisualizaciones(Integer contadorVisualizaciones) {
        this.contadorVisualizaciones = contadorVisualizaciones;
    }

    public Integer getContadorDescargas() {
        return contadorDescargas != null ? contadorDescargas : 0;
    }

    public void setContadorDescargas(Integer contadorDescargas) {
        this.contadorDescargas = contadorDescargas;
    }

    // Nuevo campo para el archivo PDF
    @Column(name = "archivo_pdf")
    private String archivoPdf; // Nombre del archivo PDF almacenado

    @ManyToOne
    @JoinColumn(name = "codigo_autor")
    private Autor autor;

    // Constructores
    public Libro() {}

    public Libro(String titulo, String editorial, Integer paginas, String archivoPdf, Autor autor) {
        this.titulo = titulo;
        this.editorial = editorial;
        this.paginas = paginas;
        this.archivoPdf = archivoPdf;
        this.autor = autor;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getEditorial() {
        return editorial;
    }

    public void setEditorial(String editorial) {
        this.editorial = editorial;
    }

    public Integer getPaginas() {
        return paginas;
    }

    public void setPaginas(Integer paginas) {
        this.paginas = paginas;
    }

    public String getArchivoPdf() {
        return archivoPdf;
    }

    public void setArchivoPdf(String archivoPdf) {
        this.archivoPdf = archivoPdf;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }


}
