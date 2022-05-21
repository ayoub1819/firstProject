package com.example.projectge.service;

import com.example.projectge.DAO.AppelOffreRepository;
import com.example.projectge.DAO.DemandeRepository;
import com.example.projectge.DAO.OffreRepository;
import com.example.projectge.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
public class OffreServiceImp implements OffreService{
    @Autowired
    private OffreRepository offreRepository;
    @Autowired
    private AppelOffreRepository appelOffreRepository;
    @Autowired
    private DemandeRepository demandeRepository;
    @Autowired
    private DemandeService demandeService;
    @Autowired
    private AccountService accountService;

    @Override
    public List<AppelOffre> findAll() {
        List<AppelOffre> appelOffres = appelOffreRepository.findAll(Sort.by(Sort.Direction.DESC,"datePublication"));
        return appelOffres;
    }

    @Override
    public AppelOffre findAppelOffreById(Long id) {
        return appelOffreRepository.findById(id).orElse(null);
    }

    @Override
    public void submitOffer(Offre offre) {
        AppelOffre appelOffre = findAppelOffreById(offre.getIdAppelOffre());
        offre.setAccepted(false);
        offre.setEtat("en attente");
        offre.setAppelOffre(appelOffre);
        Offre newOffre = offreRepository.save(offre);
        appelOffre.getOffres().add(newOffre);
        appelOffreRepository.save(appelOffre);
    }

    @Override
    public void postAppelOffre() {
        List<Demande_departement> demande_en_attente = demandeRepository.findDemande_departementByEtat("en attente");
        AppelOffre appelOffre = new AppelOffre();
        appelOffre.setDatePublication(String.valueOf(new Date()));
        demande_en_attente.forEach(demande -> {
            demande.setEtat("publié");
            demandeRepository.save(demande);
        });
        appelOffre.setDemande_departements(demande_en_attente);
        appelOffre.setAffected(false);
        appelOffreRepository.save(appelOffre);

    }

    @Override
    public void acceptOffer(Offre offre) {
        AppelOffre appelOffre = findAppelOffreById(offre.getIdAppelOffre());
        String etat="";
        for (Offre item : appelOffre.getOffres()) {
            item.setAccepted(Objects.equals(item.getId(), offre.getId()));
            etat = (item.isAccepted())?"accepté":"rejeter";
            item.setEtat(etat);
            offreRepository.save(item);
        }
        for (Demande_departement demande_departement : appelOffre.getDemande_departements()) {
            demande_departement.setEtat("en commande");
            demandeRepository.save(demande_departement);
        }
        appelOffre.setAffected(true);
        appelOffreRepository.save(appelOffre);
    }

    @Override
    public List<Demande_departement> demandesEnCour() {
        return demandeRepository.findDemande_departementByEtat("en attente");
    }
    @Override
    public String getAppelOffreResume(AppelOffre appelOffre,boolean isNotEditable){
        //List<AppelOffre> appelOffres = findAll();
        List<Besoin> besoins = new ArrayList<>();
        StringBuilder html = new StringBuilder();
        String header = """
                <div class="card-header d-flex">                                   
                    <div class="author-info">
                        <small class="text-muted">%s</small>
                    </div>
                </div>
                """;
        String footer = """
                <div class="card-footer d-flex justify-content-center align-items-center">                                
                          <button id=%d class="btn btn-outline-primary round waves-effect envoyer_offre_modale_button" data-bs-toggle="modal" data-bs-target="#envoyer_offre_modale">envoyer offre</button>
                </div>
                """;
        String blog = """
                <div  class="card col-md-6 col-12">
                    %s            
                </div>
                """;
        for (Demande_departement demande_departement : appelOffre.getDemande_departements())
            besoins.addAll(demande_departement.getBesoin());
        String body = demandeService.getDemandesResume(besoins);
        String finale = String.format(header,appelOffre.getDatePublication()) +
                body +
                ( (isNotEditable)? "":String.format(footer,appelOffre.getId()) ) ;
        return String.format(blog,finale);
        /*
        for (AppelOffre appelOffre : appelOffres) {
            for (Demande_departement demande_departement : appelOffre.getDemande_departements()) {
                besoins.addAll(demande_departement.getBesoin());
            }
            body = demandeService.getDemandesResume(besoins);
            finale = String.format(header,appelOffre.getDatePublication()) +
                     body +
                    ( (appelOffre.getOffre() == null)? "":String.format(footer,appelOffre.getId()) ) ;
            html.append(String.format(blog,finale));
            besoins.clear();
        }
        return html.toString();
        */
    }

    @Override
    public boolean isUserSendOffer(AppelOffre appelOffre, String username) {
        for (Offre offre : appelOffre.getOffres())
            if (offre.getUser().getUsername().equals(username))
                return true;
        return false;
    }

    @Override
    public Offre findOffreByid(Long id) {
        return offreRepository.findById(id).orElse(null);
    }

    @Override
    public List<Offre> findOffre(String username) {
        User user = accountService.findUserByUserName(username);
        List<Offre> offres = offreRepository.findOffreByUser(user);
        return offres;
    }
}
