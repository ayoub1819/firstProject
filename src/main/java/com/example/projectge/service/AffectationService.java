package com.example.projectge.service;

import com.example.projectge.models.Affectation;
import com.example.projectge.models.Ressource;

public interface AffectationService {
    Affectation findByRessource(Long ressource);
    void delete(Affectation affectation);
}
