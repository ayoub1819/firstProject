package com.example.projectge.DAO;

import com.example.projectge.models.Offre;
import com.example.projectge.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OffreRepository extends JpaRepository<Offre,Long> {
    List<Offre> findOffreByUser(User user);
}
