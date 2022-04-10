package com.example.projectge.controllers;

import com.example.projectge.models.Besoin;
import com.example.projectge.service.BesionsService;
import com.example.projectge.service.MembreDepartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;

@Controller
public class BesionDemandesController {
    @Autowired
    private BesionsService besionsService;
    @Autowired
    private MembreDepartService membreDepartService;

    @GetMapping(path = "/besoin")
    public String sendBesoinForm(Model model,Principal principal){
        var membre = membreDepartService.findMembreByUserName(principal.getName());
        var isChef = membre.getCompte().getRole().stream().anyMatch(r -> r.getRoleName().equals("CHEF_DEP"));
        Besoin memBesoin = besionsService.getMemberBesoin(membre.getCIN());
        Besoin depBesoin = besionsService.getDepartementBesoin(membre.getDepartement());
        var depRessources = besionsService.test(depBesoin);
        var ressources = besionsService.test(memBesoin);
        if (isChef){
            model.addAttribute("depbesoin",depBesoin);
            model.addAttribute("depressources",depRessources);
        }
        model.addAttribute("besoin",memBesoin);
        model.addAttribute("ressources",ressources);
        return "/ofresEtBesoin/besoins";
    }
    @PostMapping(path = "/addBesoin")
    @ResponseBody
    public String addBesoin(@RequestBody Besoin besoin, Principal principal){
        var membre = membreDepartService.findMembreByUserName(principal.getName());
        var isChef = membre.getCompte().getRole().stream().anyMatch(r -> r.getRoleName().equals("CHEF_DEP"));
        besoin.setDeparetement(membre.getDepartement());
        if (isChef && besoin.getMembre() == null)  return besionsService.save(besoin).toString();
        besoin.setMembre(membre);
        return besionsService.save(besoin).toString();
    }

    @GetMapping(path = "/Chefbesoin")
    public String ChefBesoin(){
        return "/ofresEtBesoin/besoinsChefDepartemen";
    }
}
