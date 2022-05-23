package com.example.projectge.DAO;

import com.example.projectge.models.Affectation;
import com.example.projectge.models.Departement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;

public interface DepartementRepository extends JpaRepository<Departement,Long> {
    @Transactional
    @Modifying

    @Query("update Departement d set d.Nom_departement=?1 where d.id_departement=?2  ")
    void update(String nom,Long id);
}
