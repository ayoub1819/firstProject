package com.example.projectge.service;

import com.example.projectge.models.Membre_departement;

public interface MembreDepartService {
    Membre_departement findMembreByUserName(String username);
}
