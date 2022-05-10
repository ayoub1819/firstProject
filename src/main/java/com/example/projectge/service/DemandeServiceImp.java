package com.example.projectge.service;

import com.example.projectge.DAO.DemandeRepository;
import com.example.projectge.models.Demande_departement;
import com.example.projectge.models.Departement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DemandeServiceImp implements DemandeService{
    @Autowired
    private DemandeRepository demandeRepository;
    @Override
    public void save(Demande_departement demande) {
        demandeRepository.save(demande);
    }

    @Override
    public List<Demande_departement> findAllDepartementDemandes(Departement departement) {
        return demandeRepository.findDemande_departementByDepartement(departement);
    }

    @Override
    public Demande_departement find(Long id) {
        return demandeRepository.findById(id).orElse(null);
    }
}
