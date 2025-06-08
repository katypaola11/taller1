package com.itsqmet.entidad;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;

@Entity
@Data
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(min=3, max=15, message = "solo letras")
    private String nombreCompleto;

    @NotNull
    private  int edad;

    @Column(unique = true)
    @Email (message = "El formato del email no es válido")
    private String email;


    @NotBlank(message = "La contraseña es obligatoria")
    @Size(min = 8, max = 15, message = "La contraseña debe tener entre 8 y 15 caracteres")
    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)[A-Za-z\\d@$!%*?&._-]+$",
            message = "La contraseña debe contener al menos una letra y un número")
    private String password;


    @Pattern(regexp = "^[+]?[0-9]{7,10}$", message = "El teléfono debe tener entre 7 y 10 dígitos")
    @Column(length = 10)
    private String telefono;

    @NotBlank
    private String ocupacion;

    @Size(min=3, max=15, message = "es obligatorio")
    private String interes;

}








