package com.example.projectge.service;

import com.example.projectge.DAO.Membre_departementRepository;
import com.example.projectge.DAO.UserRepository;
import com.example.projectge.models.Membre_departement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MembreDepartServiceImp implements MembreDepartService{
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private Membre_departementRepository membre_departementRepository;

    @Override
    public Membre_departement findMembreByUserName(String username) {
        var user = userRepository.findUserByUsername(username);
        return membre_departementRepository.findMembre_departementByCompte(user);
    }
}
