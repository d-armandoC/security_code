package com.registro.registro.Controller;

import com.registro.registro.Model.Persona;
import com.registro.registro.Service.IPersonaService;
import com.registro.registro.dto.request.PersonaRequestDTO;
import com.registro.registro.dto.response.PersonaResponseDTO;

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

        existente.setNombre(dto.getNombre());
        existente.setApellido(dto.getApellido());
        existente.setCedula(dto.getCedula());
        existente.setEmail(dto.getEmail());
        existente.setCelular(dto.getCelular());
        existente.setCarrera(dto.getCarrera());
        existente.setInsti(dto.getInsti());

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

    private Persona toEntity(PersonaRequestDTO dto) {
        Persona p = new Persona();
        p.setNombre(dto.getNombre());
        p.setApellido(dto.getApellido());
        p.setCedula(dto.getCedula());
        p.setEmail(dto.getEmail());
        p.setCelular(dto.getCelular());
        p.setCarrera(dto.getCarrera());
        p.setInsti(dto.getInsti());
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
