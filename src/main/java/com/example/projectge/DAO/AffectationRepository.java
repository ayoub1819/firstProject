package com.example.projectge.DAO;

import com.example.projectge.models.Affectation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface AffectationRepository extends JpaRepository<Affectation,Long> {
}
