package com.example.projectge.service;

import com.example.projectge.DAO.AffectationRepository;
import com.example.projectge.models.Affectation;
import com.example.projectge.models.Ressource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
