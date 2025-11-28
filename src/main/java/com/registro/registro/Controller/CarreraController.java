package com.registro.registro.Controller;

import com.registro.registro.Model.Carrera;
import com.registro.registro.Service.ICarreraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = {"${frontend.url}"})
@RestController
@RequestMapping("/api")
public class CarreraController {

    @Autowired
    public ICarreraService carreraService;

    @GetMapping("/carrera")
    public List<Carrera> listarPersonas(){
        return carreraService.findAll();
    }
}
