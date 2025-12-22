package com.registro.registro.Dao;

import com.registro.registro.Model.Persona;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IPersonaDao extends JpaRepository<Persona,Long> {
 boolean existsByCedula(String cedula);
}
