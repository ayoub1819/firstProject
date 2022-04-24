package com.example.projectge.DAO;

import com.example.projectge.models.Affectation;

import com.example.projectge.models.Ressource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AffectationRepository extends JpaRepository<Affectation,Long> {
    List<Affectation> findAffectationByMemmbre(Long idMembre);
    List<Affectation> findAffectationByDepartementAndMemmbreNull(Long id_dep);
    Affectation findAffectationByRessource(Long ressource);
}
