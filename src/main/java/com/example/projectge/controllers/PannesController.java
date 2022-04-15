package com.example.projectge.controllers;


import com.example.projectge.models.*;
import com.example.projectge.service.MembreDepartService;
import com.example.projectge.service.PannesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class PannesController {
    @Autowired
    private PannesService pannesService;
    @Autowired
    private MembreDepartService membreDepartService;
    @GetMapping(path = "/signalerPanne")
    String signalerPanneIndex(Model model,Principal principal){
        List<Ressource> ressources = pannesService.getRessources(principal.getName());
        List<Affectation> affectations = pannesService.getAffectations(principal.getName());
        List<Imprimente> imprimentes = new ArrayList<>();
        List<Ordinateur> ordinateurs = new ArrayList<>();
        Map<Long,String> etats = new HashMap<>();
        for (Ressource ressource : ressources) {
            etats.put(ressource.getId(),pannesService.etat(ressource));
            if (ressource instanceof Imprimente) imprimentes.add((Imprimente) ressource);
            if (ressource instanceof Ordinateur) ordinateurs.add((Ordinateur) ressource);
        }
        Map<Long,Affectation> affectationMap = new HashMap<>();
        affectations.forEach(affectation -> affectationMap.put(affectation.getRessource(),affectation));
        model.addAttribute("imprimentes",imprimentes);
        model.addAttribute("ordinateurs",ordinateurs);
        model.addAttribute("affectations",affectationMap);
        model.addAttribute("etats",etats);
        return "pannes/signalerPanne";
    }
    @PostMapping(path = "/addPanne")
    @ResponseBody
    String addPanne(@RequestBody Panne panne,Principal principal){
        Membre_departement membre = membreDepartService.findMembreByUserName(principal.getName());
        panne.setEtat("en attente");
        panne.setMembre_departement(membre);
        pannesService.save(panne);
        return "success";
    }
}
