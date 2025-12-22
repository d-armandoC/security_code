package com.registro.registro.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PersonaResponseDTO {
    private Long idPersona;
    private String nombre;
    private String apellido;
    private String cedula;
    private String email;
    private String celular;
    private String carrera;
    private String insti;
}
