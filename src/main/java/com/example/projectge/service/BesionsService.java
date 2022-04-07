package com.example.projectge.service;

import com.example.projectge.DAO.BesionRepository;
import com.example.projectge.DAO.Membre_departementRepository;
import com.example.projectge.models.Besoin;
import com.example.projectge.models.Membre_departement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BesionsService {
    @Autowired
    private BesionRepository besionRepository;
    @Autowired
    private Membre_departementRepository membre_departementRepository;

    public List<String[]> test(Besoin besoin){
        String[] ressources = besoin.getRessource().split(";");
        List<String[]> listRessources = new ArrayList<>();
        for (String ressource : ressources) {
            listRessources.add(ressource.split(","));
        }
        return listRessources;
    }
    public Besoin findByMember(Long id){
        Membre_departement membre_departement = membre_departementRepository.findById(id).orElse(null);
        return besionRepository.findByMembreAndEtatFalse(membre_departement);
    }

}
