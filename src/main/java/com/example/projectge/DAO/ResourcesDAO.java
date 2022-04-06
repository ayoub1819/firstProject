package com.example.projectge.DAO;

import com.example.projectge.models.Ressource;
import com.example.projectge.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResourcesDAO extends JpaRepository<Ressource,Long> {


    List<Ressource> findByAffecte(boolean isa);
}
