package com.example.projectge.controllers;



import com.example.projectge.models.*;
import com.example.projectge.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;


@Controller
public class OffreController {
    @Autowired
    private OffreService offreService;
    @Autowired
    private BesionsServiceImp besionsServiceImp;
    @Autowired
    private DemandeService demandeService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private FournisseurService fournisseurService;

    @GetMapping(path = "/Appeloffres")
    String fournisseurOffersIndex(Model model, Principal principal){
        List<AppelOffre> appelOffres = offreService.findAll();
        StringBuilder html = new StringBuilder();
        for (AppelOffre appelOffre : appelOffres) {
            boolean isUserSendOffer = offreService.isUserSendOffer(appelOffre,principal.getName());
            boolean isAppelAcepted = appelOffre.isAffected();
            boolean isUserBlackList = fournisseurService.isUserBlackList(principal.getName());
            boolean forbidenToSendOffer = (isUserSendOffer || isAppelAcepted || isUserBlackList);
            System.out.println("isUserSendOffer : "+isUserSendOffer);
            html.append(offreService.getAppelOffreResume(appelOffre, forbidenToSendOffer));
        }

        model.addAttribute("html",html);
        return "ofresEtBesoin/fournisseurAppelOffre";
    }

    @GetMapping(path = "/postappeloffre")
    String publierAppelOfferIndex(Model model, Principal principal){
        List<Demande_departement> demande_departements = offreService.demandesEnCour();
        model.addAttribute("demandes",demande_departements);
        return "ofresEtBesoin/publierAppelOffre";
    }
    @PostMapping(path = "/postappeloffre")
    @ResponseBody
    String publierAppelOffer(){
        offreService.postAppelOffre();
        return "success";
    }
    @PostMapping(path = "/envoyerAppelOffreModalForm")
    @ResponseBody
    String AppelOfferResume(){
        List<Demande_departement> demande_departements = offreService.demandesEnCour();
        List<Besoin> besoins = new ArrayList<>();
        for (Demande_departement demande_departement : demande_departements) {
            besoins.addAll(demande_departement.getBesoin());
        }

        return demandeService.getDemandesResume(besoins);
    }
    @PostMapping(path = "/envoyerOffre")
    @ResponseBody
    String envoyerOffre(@RequestBody Offre offre, Principal principal){
        User user = accountService.findUserByUserName(principal.getName());
        offre.setUser(user);
        offreService.submitOffer(offre);
        return "success";
    }

    @GetMapping(path = "/gestionappeloffre")
    String gestionAppelOfferIndex(Model model, Principal principal){
        List<AppelOffre> appelOffres = offreService.findAll();

        model.addAttribute("appelOffres",appelOffres);
        return "ofresEtBesoin/gestionAppelOffre";
    }

    @PostMapping(path = "/AppelOffreDetail")
    @ResponseBody
    public String getSuivreDemandeResume(@RequestBody Long id){
        AppelOffre appelOffre = offreService.findAppelOffreById(id);
        List<Demande_departement> demande_departements = appelOffre.getDemande_departements();
        List<Besoin> besoins = new ArrayList<>();
        for (Demande_departement demande_departement : demande_departements) {
            besoins.addAll(demande_departement.getBesoin());
        }

        return demandeService.getDemandesResume(besoins);
    }

    @GetMapping(path = "/accepterOffre")
    public String accepterOffreIndex(Model model,Long id){
        AppelOffre appelOffre = offreService.findAppelOffreById(id);
        List<Offre> offres = appelOffre.getOffres();
        model.addAttribute("offres",offres);
        return "ofresEtBesoin/accepterOffre";
    }

    @PostMapping(path = "/accepterOffre")
    @ResponseBody
    public String accepterOffre(@RequestBody Long id){
        Offre offre = offreService.findOffreByid(id);
        offreService.acceptOffer(offre);
        return "success";
    }

    @GetMapping(path = "/gestionOffres")
    String gestionOffresIndex(Model model, Principal principal){
        List<Offre> offres = offreService.findOffre(principal.getName());

        model.addAttribute("offres",offres);
        return "ofresEtBesoin/offreEnvoyerFournisseur";
    }
}
