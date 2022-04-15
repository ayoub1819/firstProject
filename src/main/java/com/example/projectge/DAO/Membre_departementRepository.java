package com.example.projectge.DAO;

import com.example.projectge.models.Affectation;
import com.example.projectge.models.Departement;
import com.example.projectge.models.Membre_departement;
import com.example.projectge.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface Membre_departementRepository extends JpaRepository<Membre_departement,Long> {
    Membre_departement findMembre_departementByCompte(User user);
    List<Membre_departement> findMembre_departementByDepartement(Departement departement);
}
