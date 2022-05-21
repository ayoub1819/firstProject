package com.example.projectge.controllers;

import com.example.projectge.models.Fournisseur;
import com.example.projectge.models.Offre;
import com.example.projectge.models.User;
import com.example.projectge.service.AccountService;
import com.example.projectge.service.FournisseurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;
import java.util.List;

@Controller
public class GestionFournisseurController {
    @Autowired
    private FournisseurService fournisseurService;
    @Autowired
    private AccountService accountService;

    @GetMapping(path = "/gestionController")
    String gestionOffresIndex(Model model, Principal principal){
        List<Fournisseur> fournisseurs = fournisseurService.findAll();
        List<User> users = fournisseurService.findAllUsersNonFournisseur();
        model.addAttribute("fournisseurs",fournisseurs);
        model.addAttribute("users",users);
        return "gestionFournisseur/gestionFournisseur";
    }

    @PostMapping (path = "/ajouterFournisseur")
    @ResponseBody
    String ajouterFournisseur(@RequestBody Fournisseur fournisseur, Principal principal){
        User user = accountService.findUserByUserName(fournisseur.getCompte().getUsername());
        fournisseur.setCompte(user);
        fournisseur.setBlackList(false);
        fournisseurService.save(fournisseur);
        return "success";
    }
    @PostMapping (path = "/blockFournisseur")
    @ResponseBody
    String blockerFournisseur(@RequestBody Fournisseur fournisseur, Principal principal){
        Fournisseur fournisseur1 = fournisseurService.find(fournisseur.getCompte().getUsername());
        fournisseur1.setBlackList(true);
        fournisseur1.setMotifDeBlockage(fournisseur.getMotifDeBlockage());
        fournisseurService.save(fournisseur1);
        return "success";
    }


}
