package com.registro.registro.Controller;

import com.registro.registro.Model.Persona;
import com.registro.registro.Service.IPersonaService;
import com.registro.registro.dto.request.PersonaRequestDTO;
import com.registro.registro.dto.response.PersonaResponseDTO;

// IMPORTANTE: Estas librerías habilitan el candado en el controlador
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/api")
@Validated
// 1. Vincula este controlador con el esquema "bearerAuth" definido en SecurityConfig
@SecurityRequirement(name = "bearerAuth")
// 2. Etiqueta para organizar Swagger
@Tag(name = "Persona", description = "Gestión de registros de personas")
public class PersonaController {

    private final IPersonaService personaService;

    public PersonaController(IPersonaService personaService) {
        this.personaService = personaService;
    }

    @GetMapping("/persona")
    public ResponseEntity<List<PersonaResponseDTO>> listarPersonas() {
        List<PersonaResponseDTO> out = personaService.findAll()
                .stream()
                .map(this::toResponse)
                .collect(toList());
        return ResponseEntity.ok(out);
    }

    @GetMapping("/persona/{id}")
    public ResponseEntity<PersonaResponseDTO> personaById(@PathVariable @Min(1) Long id) {
        Persona persona = personaService.findById(id);
        if (persona == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(toResponse(persona));
    }

    @PostMapping("/persona")
    public ResponseEntity<PersonaResponseDTO> crear(@Valid @RequestBody PersonaRequestDTO dto) {
        Persona persona = toEntity(dto);
        Persona created = personaService.save(persona);
        return ResponseEntity.status(HttpStatus.CREATED).body(toResponse(created));
    }

    @PutMapping("/persona/{id}")
    public ResponseEntity<PersonaResponseDTO> actualizar(@PathVariable @Min(1) Long id,
                                                         @Valid @RequestBody PersonaRequestDTO dto) {

        Persona existente = personaService.findById(id);
        if (existente == null) return ResponseEntity.notFound().build();

        actualizarCampos(existente, dto);

        Persona guardada = personaService.save(existente);
        return ResponseEntity.ok(toResponse(guardada));
    }

    @DeleteMapping("/persona/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable @Min(1) Long id) {
        Persona persona = personaService.findById(id);
        if (persona == null) return ResponseEntity.notFound().build();

        personaService.delete(id);
        return ResponseEntity.noContent().build();
    }

    // --- Métodos de apoyo para limpieza de código ---

    private void actualizarCampos(Persona p, PersonaRequestDTO dto) {
        p.setNombre(dto.getNombre());
        p.setApellido(dto.getApellido());
        p.setCedula(dto.getCedula());
        p.setEmail(dto.getEmail());
        p.setCelular(dto.getCelular());
        p.setCarrera(dto.getCarrera());
        p.setInsti(dto.getInsti());
    }

    private Persona toEntity(PersonaRequestDTO dto) {
        Persona p = new Persona();
        actualizarCampos(p, dto);
        return p;
    }

    private PersonaResponseDTO toResponse(Persona p) {
        return new PersonaResponseDTO(
                p.getIdPersona(),
                p.getNombre(),
                p.getApellido(),
                p.getCedula(),
                p.getEmail(),
                p.getCelular(),
                p.getCarrera(),
                p.getInsti()
        );
    }
}