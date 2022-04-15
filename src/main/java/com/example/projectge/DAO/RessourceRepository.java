package com.example.projectge.DAO;

import com.example.projectge.models.Ressource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RessourceRepository extends JpaRepository<Ressource,Long> {


    List<Ressource> findByAffecte(boolean isa);
}
