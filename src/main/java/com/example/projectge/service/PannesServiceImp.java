package com.example.projectge.service;

import com.example.projectge.DAO.*;
import com.example.projectge.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Date;
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
    public List<Panne> unseenPannes() {
        return pnneRepository.findPanneByConstatIsNull();
    }

    @Override
    public List<Panne> unprocessedPannes() {
        List<Panne> pannes = pnneRepository.findPanneByEtat("en traitement");
        pannes.addAll(pnneRepository.findPanneByEtat("attente du garrantie"));
        return pannes;
    }

    @Override
    public Panne findById(Long id) {
        return pnneRepository.findById(id).orElse(null);
    }

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

    @Override
    public String warrantiesDuration(Ressource ressource) {
        String strDate = ressource.getDate_liv();
        long duration = Integer.parseInt(ressource.getGarantie());
        LocalDate livraisonDate = LocalDate.parse(strDate);
        LocalDate warrantDate = livraisonDate.plusMonths(duration);
        LocalDate today = LocalDate.now();
        var isExpired = warrantDate.isBefore(LocalDate.now());
        if (isExpired) return "expiré";
        Period period = Period.between(today,warrantDate);
        String warrantDuration = period.getMonths()+" mois  "+period.getDays()+" jours";
        return warrantDuration;
    }
}
