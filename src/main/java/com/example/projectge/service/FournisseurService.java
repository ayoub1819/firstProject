package com.example.projectge.service;

import com.example.projectge.models.Fournisseur;
import com.example.projectge.models.User;

import java.util.List;

public interface FournisseurService {
    Fournisseur find(String username);
    boolean isUserBlackList(String username);
    List<Fournisseur> findAll();
    List<User> findAllUsersNonFournisseur();
    void save(Fournisseur fournisseur);
}
