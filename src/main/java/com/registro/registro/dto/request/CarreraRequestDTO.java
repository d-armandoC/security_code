package com.registro.registro.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CarreraRequestDTO {

    @NotBlank(message = "El nombre de la carrera es obligatorio")
    @Size(min = 3, max = 100, message = "El nombreCarrera debe tener entre 3 y 100 caracteres")
    private String nombreCarrera;
}
