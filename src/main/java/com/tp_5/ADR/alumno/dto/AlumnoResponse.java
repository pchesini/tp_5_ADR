package com.tp_5.ADR.alumno.dto;

import java.time.LocalDate;

import lombok.*;
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class AlumnoResponse {
        private Long id;
    private String nombre;
    private String apellido;
    private String email;
    private LocalDate fechaNacimiento;
    private String telefono;
    private String direccion;
}
