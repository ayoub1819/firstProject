package com.example.projectge.service;

import com.example.projectge.models.Besoin;
import com.example.projectge.models.Demande_departement;
import com.example.projectge.models.Departement;

import java.util.List;

public interface DemandeService {
    void save(Demande_departement demande);
    List<Demande_departement> findAllDepartementDemandes(Departement departement);
    Demande_departement find(Long id);
    String getDemandesResume(List<Besoin> besoins);
}
