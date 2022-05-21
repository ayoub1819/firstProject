package com.example.projectge.controllers;


import com.example.projectge.models.*;
import com.example.projectge.service.AffectationService;
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
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Controller
public class PannesController {
    @Autowired
    private PannesService pannesService;
    @Autowired
    private MembreDepartService membreDepartService;
    @Autowired
    private AffectationService affectationService;

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

    @GetMapping(path = "/maintenance")
    String seviceMaintenanceIndex(Model model,Principal principal){
        List<Panne> pannes = pannesService.unseenPannes();
        Map<Long,Ordinateur> PC = new HashMap<>();
        Map<Long,Imprimente> imp = new HashMap<>();
        pannes.forEach(p -> {
            if (p.getRessource() instanceof Ordinateur) PC.put(p.getId(),(Ordinateur) p.getRessource());
            if (p.getRessource() instanceof Imprimente) imp.put(p.getId(), (Imprimente) p.getRessource());
        });
        Predicate<Panne> isPC = p -> p.getRessource() instanceof Ordinateur;
        Predicate<Panne> isImp = p -> p.getRessource() instanceof Imprimente;
        List<Panne> pannesPC = pannes.stream().filter(isPC).toList();
        List<Panne> pannesImp = pannes.stream().filter(isImp).toList();
        model.addAttribute("PCs",pannesPC);
        model.addAttribute("Imps",pannesImp);
        model.addAttribute("ordinateur",PC);
        model.addAttribute("imprimente",imp);
        return "pannes/envoyerConstat";
    }
    @GetMapping(path = "/responsableMaintenance")
    String responsableMaintenanceIndex(Model model){
        List<Panne> pannes = pannesService.unprocessedPannes();
        Map<Long,Ordinateur> PC = new HashMap<>();
        Map<Long,Imprimente> imp = new HashMap<>();
        Map<Long,String> grantie = new HashMap<>();
        pannes.forEach(p -> {
            String garantieDuration = pannesService.warrantiesDuration(p.getRessource());
            grantie.put(p.getId(),garantieDuration);
            if (p.getRessource() instanceof Ordinateur) PC.put(p.getId(),(Ordinateur) p.getRessource());
            if (p.getRessource() instanceof Imprimente) imp.put(p.getId(), (Imprimente) p.getRessource());
        });
        Predicate<Panne> isPC = p -> p.getRessource() instanceof Ordinateur;
        Predicate<Panne> isImp = p -> p.getRessource() instanceof Imprimente;
        List<Panne> pannesPC = pannes.stream().filter(isPC).toList();
        List<Panne> pannesImp = pannes.stream().filter(isImp).toList();
        model.addAttribute("PCs",pannesPC);
        model.addAttribute("Imps",pannesImp);
        model.addAttribute("ordinateur",PC);
        model.addAttribute("imprimente",imp);
        model.addAttribute("grantie",grantie);
        return "pannes/responsablePanne";
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

    @PostMapping(path = "/addConstat")
    @ResponseBody
    String addConstat(@RequestBody Panne panne){
        Panne p = pannesService.findById(panne.getId());
        String garantieDuration = pannesService.warrantiesDuration(p.getRessource());
        boolean sousGarrantie = !garantieDuration.equals("expiré");
        boolean isPanneMateriel = (p.getOrdre().getDisplayValue().equals("materiel"));
        boolean sendConstat = sousGarrantie && isPanneMateriel;
        System.out.println("sousGarrantie:"+sousGarrantie+" isPanneMateriel: "+isPanneMateriel+"  sendConstat: "+sendConstat);
        p.setConstat(panne.getConstat());
        p.setEtat("en traitement");
        if (sendConstat){
            p.setEtat("attente du garrantie");
            //code for sending email to the provider
        }
        pannesService.save(p);
        return "success";
    }
    @PostMapping(path = "/makeDecision")
    @ResponseBody
    String makeDecision(@RequestBody Panne panne){
        Panne p = pannesService.findById(panne.getId());
        switch (panne.getEtat()){
            /*
            case "envoyer":
                        p.setEtat("en garantie");
                        // code of sending email to the provider
                        break;
                        */
            case "reparer":
                        p.setEtat("réparer");
                        break;
            case "changer":
                        p.setEtat("changé");
                        //Affectation affectation = affectationService.findByRessource(p.getRessource().getId());
                        //affectationService.delete(affectation);
                        break;
        }
        pannesService.save(p);
        return "success";
    }

}
