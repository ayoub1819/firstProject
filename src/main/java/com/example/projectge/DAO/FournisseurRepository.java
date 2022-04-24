package com.example.projectge.DAO;

import com.example.projectge.models.Fournisseur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface FournisseurRepository extends JpaRepository<Fournisseur,String> {
    Fournisseur findFournisseurByCIN(String cin);

}
