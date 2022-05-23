package com.example.projectge.service;

import com.example.projectge.models.Affectation;
import com.example.projectge.models.Ressource;
import java.util.*;

public interface AffectationService {
    Affectation findByRessource(Long ressource);
    void delete(Affectation affectation);
    List<Affectation> findAll();
    Affectation getById(Long id);
    void save(Affectation affectation);
    List<Affectation> findAffectationByMembre(Long id);
}
