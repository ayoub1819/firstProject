package com.example.projectge.service;

import com.example.projectge.DAO.*;
import com.example.projectge.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class PannesServiceImp implements PannesService{
    @Autowired
    private AffectationRepository affectationRepository;
    @Autowired
    private RessourceRepository resourcesRepository;
    @Autowired
    private Membre_departementRepository membre_departementRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PnneRepository pnneRepository;
    @Override
    public List<Ressource> getRessources(String username) {
        List<Affectation> affectations = getAffectations(username);
        List<Ressource> ressources = new ArrayList<>();
        for (Affectation affectation : affectations) {
            var ress = resourcesRepository.findById(affectation.getRessource()).orElse(null);
            if (ress != null)
                ressources.add(ress);
        }
        return ressources;
    }
    @Override
    public List<Affectation> getAffectations(String username) {
        User user = userRepository.findUserByUsername(username);
        Boolean isChef = user.getRole().stream().anyMatch(role -> role.getRoleName().equals("CHEF_DEP"));
        Membre_departement membre = membre_departementRepository.findMembre_departementByCompte(user);
        List<Affectation> affectations = affectationRepository.findAffectationByMemmbre(membre.getId());
        if (isChef){
            List<Affectation> depAffectations = affectationRepository.findAffectationByDepartementAndMemmbreNull(membre.getDepartement().getId_departement());
            if (depAffectations!=null)
                affectations.addAll(depAffectations);
        }
        return affectations;
    }

    @Override
    public void save(Panne panne) {
        pnneRepository.save(panne);
    }

    @Override
    public String etat(Ressource ressource) {
        String etat = "pas de réclamation";
        List<Panne> pannes=pnneRepository.findPanneByRessourceOrderByIdDesc(ressource);
        if (pannes.isEmpty()) return etat;
        Panne panne = pannes.get(0);
        return panne.getEtat();

    }
}
