package com.registro.registro.Service;

import com.registro.registro.Dao.IPersonaDao;
import com.registro.registro.Model.Persona;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PersonaServiceImplement implements IPersonaService{

    @Autowired
    public IPersonaDao personaDao;

    @Override
    @Transactional(readOnly = true)
    public List<Persona> findAll() {
        return personaDao.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Persona findById(Long id) {
        return personaDao.findById(id).orElse(null);
    }

//    @Override
//    public Persona save(Persona persona) {
//        return personaDao.save(persona);
//    }

    @Override
    public void delete(Long id) {
        personaDao.deleteById(id);
    }
@Override
  public Persona save(Persona persona) {
        // Verificar si ya existe una persona con la misma cédula
        if (personaDao.existsByCedula(persona.getCedula())) {
            throw new IllegalArgumentException("La persona ya está registrada.");
        }
        return personaDao.save(persona);
    }
}
