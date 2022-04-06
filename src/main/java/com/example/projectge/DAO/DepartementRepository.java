package com.example.projectge.DAO;

import com.example.projectge.models.Affectation;
import com.example.projectge.models.Departement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface DepartementRepository extends JpaRepository<Departement,Long> {
}
