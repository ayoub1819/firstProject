package com.example.projectge.service;

import com.example.projectge.models.Departement;
import com.example.projectge.models.Membre_departement;

import java.util.List;
import java.util.Optional;

public interface MembreDepartService {
    List<Membre_departement> findAll();
    Membre_departement findMembreByUserName(String username);
    List<Membre_departement> findByDep(Departement departement);
    String memberPost(Membre_departement membre_departement);
    void save(Membre_departement membre_departement);
    Membre_departement findById(Long id);

    Membre_departement getById(Long id);



    void delete(Membre_departement membre_departement);
}
