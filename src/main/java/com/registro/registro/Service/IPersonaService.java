package com.registro.registro.Service;

import com.registro.registro.Model.Persona;

import java.util.List;

public interface IPersonaService {

    public List<Persona> findAll();
    public Persona findById(Long id);
    public Persona save(Persona persona);
    public void delete(Long id);
}
