package com.registro.registro.Controller;

import com.registro.registro.Model.Persona;
import com.registro.registro.Service.IPersonaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@CrossOrigin(origins = {"${frontend.url}"})
@RestController
@RequestMapping("/api")
@Validated // <-- habilita validación también en path variables, etc.
public class PersonaController {

    @Autowired
    public IPersonaService personaService;

    // =========================
    //  LISTAR (Lectura general)
    // =========================
    @PreAuthorize("hasAnyRole('ADMIN','USER')")  // Autorización a nivel de método
    @GetMapping("/persona")
    public ResponseEntity<List<Persona>> listarPersonas() {
        List<Persona> personas = personaService.findAll();
        return ResponseEntity.ok(personas);
    }

    // =========================
    //  CONSULTAR POR ID
    // =========================
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @GetMapping("/persona/{id}")
    public ResponseEntity<Persona> personaById(
            @PathVariable @Min(1) Long id) { // Validación simple del ID
        Persona persona = personaService.findById(id);
        if (persona == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(persona);
    }

    // =========================
    //  CREAR
    // =========================
    @PreAuthorize("hasRole('ADMIN')")  // solo ADMIN puede crear
    @PostMapping("/persona")
    public ResponseEntity<Persona> crear(@Valid @RequestBody Persona persona) {
        try {
            // @Valid aplica las restricciones definidas en la entidad Persona
            Persona createdPersona = personaService.save(persona);
            return new ResponseEntity<>(createdPersona, HttpStatus.CREATED);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }
    // =========================
    //  ACTUALIZAR
    // =========================
    @PreAuthorize("hasRole('ADMIN')") // puedes adaptar la expresión según tu modelo de usuarios
    @PutMapping("/persona/{id}")
    public ResponseEntity<Persona> actualizar(
            @Valid @RequestBody Persona persona, // validamos también los nuevos datos
            @PathVariable @Min(1) Long id) {
        Persona personaActualizar = personaService.findById(id);
        if (personaActualizar == null) {
            return ResponseEntity.notFound().build();
        }
        // Actualización controlada campo a campo (evita problemas de sobre-escritura masiva)
        personaActualizar.setNombre(persona.getNombre());
        personaActualizar.setApellido(persona.getApellido());
        personaActualizar.setEmail(persona.getEmail());
        personaActualizar.setCelular(persona.getCelular());
        personaActualizar.setCedula(persona.getCedula());
        personaActualizar.setCarrera(persona.getCarrera());
        personaActualizar.setInsti(persona.getInsti());
        Persona personaGuardada = personaService.save(personaActualizar);
        return ResponseEntity.ok(personaGuardada);
    }

    // =========================
    //  ELIMINAR
    // =========================
    @PreAuthorize("hasRole('ADMIN')")  // solo ADMIN puede eliminar
    @DeleteMapping("/persona/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable @Min(1) Long id) {
        Persona persona = personaService.findById(id);
        if (persona == null) {
            return ResponseEntity.notFound().build();
        }
        personaService.delete(id);
        return ResponseEntity.noContent().build();
    }

    // =========================
    //  MANEJO DE ERRORES
    // =========================
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleException(IllegalArgumentException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }
}