package com.example.projectge.service;

import com.example.projectge.models.Ressource;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.projectge.DAO.RessourceRepository;
import java.util.List;
import java.util.Optional;

public class RessourcessServiceImp implements RessourcessService{

    @Autowired
    private RessourceRepository ressourcesRepository;


    @Override
    public List<Ressource> findAll() {
        return ressourcesRepository.findAll();
    }

    @Override

    public Ressource findById(Long id){
        return ressourcesRepository.getById(id);
    }
}
