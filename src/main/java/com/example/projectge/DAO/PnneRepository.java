package com.example.projectge.DAO;

import com.example.projectge.models.Membre_departement;
import com.example.projectge.models.Panne;
import com.example.projectge.models.Ressource;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PnneRepository extends JpaRepository<Panne,Long> {
    List<Panne> findPanneByRessourceOrderByIdDesc(Ressource ressource);
}
