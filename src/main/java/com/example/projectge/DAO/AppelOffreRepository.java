package com.example.projectge.DAO;

import com.example.projectge.models.AppelOffre;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AppelOffreRepository extends JpaRepository<AppelOffre,Long> {
    //AppelOffre findAppelOffreByOffreNull();
}
