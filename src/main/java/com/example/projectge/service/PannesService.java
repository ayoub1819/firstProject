package com.example.projectge.service;

import com.example.projectge.models.Affectation;
import com.example.projectge.models.Panne;
import com.example.projectge.models.Ressource;

import java.util.List;

public interface PannesService {
    List<Ressource> getRessources(String username);
    List<Affectation> getAffectations(String username);
    void save(Panne panne);
    String etat(Ressource ressource);
}
