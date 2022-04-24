package com.example.projectge.service;

import com.example.projectge.models.Affectation;
import com.example.projectge.models.Panne;
import com.example.projectge.models.Ressource;

import java.util.List;

public interface PannesService {
    List<Panne> unseenPannes();
    List<Panne> unprocessedPannes();
    Panne findById(Long id);
    List<Ressource> getRessources(String username);
    List<Affectation> getAffectations(String username);
    void save(Panne panne);
    String etat(Ressource ressource);
    String warrantiesDuration(Ressource ressource);
}
