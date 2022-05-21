package com.example.projectge.DAO;

import com.example.projectge.models.Demande_departement;
import com.example.projectge.models.Departement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DemandeRepository extends JpaRepository<Demande_departement,Long> {
    List<Demande_departement> findDemande_departementByDepartement(Departement departement);
    List<Demande_departement> findDemande_departementByEtat(String etat);
}
