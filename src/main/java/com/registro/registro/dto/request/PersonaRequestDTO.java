package com.registro.registro.dto.request;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class PersonaRequestDTO {

    @NotBlank @Size(min = 2, max = 80)
    private String nombre;

    @NotBlank @Size(min = 2, max = 80)
    private String apellido;

    @NotBlank
    @Size(min = 10, max = 10, message = "La cédula debe tener 10 dígitos")
    @Pattern(regexp = "^[0-9]+$", message = "La cédula solo debe contener números")
    private String cedula;

    @NotBlank @Email
    private String email;

    @NotBlank
    @Size(min = 7, max = 20)
    @Pattern(regexp = "^[0-9+]+$", message = "Celular no válido")
    private String celular;

    @NotBlank @Size(min = 3, max = 120)
    private String carrera;

    @NotBlank @Size(min = 3, max = 120)
    private String insti;
}

