package com.example.projectge.service;

import com.example.projectge.DAO.AffectationRepository;
import com.example.projectge.models.Affectation;
import com.example.projectge.models.Ressource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class AffectationServiceImp implements AffectationService{
    @Autowired
    private AffectationRepository affectationRepository;

    @Override
    public Affectation findByRessource(Long ressource) {
        return affectationRepository.findAffectationByRessource(ressource);
    }

    @Override
    public void delete(Affectation affectation) {
          affectationRepository.delete(affectation);
    }

    @Override
    public List<Affectation> findAll(){
        return affectationRepository.findAll();
    }

    @Override
    public Affectation getById(Long id) {
        return affectationRepository.getById(id);
    }

    @Override
    public void save(Affectation affectation){
        affectationRepository.save(affectation);
    }

    @Override
    public List<Affectation> findAffectationByMembre(Long id) {
        return  affectationRepository.findAffectationByMemmbre(id);
    }


}
