package com.example.projectge.service;

import com.example.projectge.DAO.BesionRepository;
import com.example.projectge.DAO.Membre_departementRepository;
import com.example.projectge.models.Besoin;
import com.example.projectge.models.Departement;
import com.example.projectge.models.Membre_departement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class BesionsServiceImp {
    @Autowired
    private BesionRepository besionRepository;
    @Autowired
    private Membre_departementRepository membre_departementRepository;

    public List<String[]> test(Besoin besoin){
        if (besoin == null) return null;
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
    public Long save(Besoin besoin){
        besoin.setDate(String.valueOf(new Date()));
        return besionRepository.save(besoin).getId();
    }
    public Besoin getMemberBesoin(Long idMember){
        var member = membre_departementRepository.findById(idMember).orElse(null);
        return besionRepository.findByMembreAndEtatFalse(member);
    }
    public Besoin getDepartementBesoin(Departement departement){
        return besionRepository.findBesoinByDeparetementAndEtatFalseAndMembreNull(departement);
    }

    public boolean isMemHasNeed(Membre_departement membre_departement){
        var besoin = besionRepository.findByMembreAndEtatFalse(membre_departement);
        return besoin != null;
    }
    public void delete(Long idMember){
        var mem = membre_departementRepository.findById(idMember).orElse(null);
        var besoin = besionRepository.findByMembreAndEtatFalse(mem);
        besionRepository.delete(besoin);
    }

}
