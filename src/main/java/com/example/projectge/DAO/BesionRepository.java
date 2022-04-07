package com.example.projectge.DAO;

import com.example.projectge.models.Besoin;
import com.example.projectge.models.Departement;
import com.example.projectge.models.Membre_departement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BesionRepository extends JpaRepository<Besoin,Long> {
    Besoin findByMembreAndEtatFalse(Membre_departement membre_departement);
    List<Besoin> findByDeparetementAndEtatFalse(Departement departement);
}
