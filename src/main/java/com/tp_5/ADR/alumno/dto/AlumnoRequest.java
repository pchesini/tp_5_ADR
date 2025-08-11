package com.tp_5.ADR.alumno.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


    
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class AlumnoRequest {
    @NotBlank @Size(max = 80) private String nombre;
    @NotBlank @Size(max = 80) private String apellido;
    @NotBlank @Email @Size(max = 120) private String email;
    @NotNull @Past private LocalDate fechaNacimiento;
    @Size(max = 30) private String telefono;
    @Size(max = 200) private String direccion;
}

