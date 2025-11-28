package com.registro.registro.Service;

import com.registro.registro.Dao.ICarreraDao;
import com.registro.registro.Model.Carrera;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CarreraServiceImplents implements  ICarreraService{

    @Autowired
    public ICarreraDao carreraDao;

    @Override
    @Transactional(readOnly = true)
    public List<Carrera> findAll() {
        return carreraDao.findAll();
    }
}
