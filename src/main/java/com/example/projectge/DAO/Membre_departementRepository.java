package com.example.projectge.DAO;

import com.example.projectge.models.Affectation;
import com.example.projectge.models.Membre_departement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;


public interface Membre_departementRepository extends JpaRepository<Membre_departement,Long> {

}
