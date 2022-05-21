package com.example.projectge.service;

import com.example.projectge.models.AppelOffre;
import com.example.projectge.models.Demande_departement;
import com.example.projectge.models.Offre;

import java.util.List;

public interface OffreService {
    List<AppelOffre> findAll();
    AppelOffre findAppelOffreById(Long id);
    void submitOffer(Offre offre);
    void postAppelOffre();
    void acceptOffer(Offre offre);
    List<Demande_departement> demandesEnCour();
    String getAppelOffreResume(AppelOffre appelOffre,boolean isblackList);
    boolean isUserSendOffer(AppelOffre appelOffre,String username);
    Offre findOffreByid(Long id);
    List<Offre> findOffre(String username);
}
