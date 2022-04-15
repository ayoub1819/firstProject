package com.example.projectge.service;

import com.example.projectge.DAO.BesionRepository;
import com.example.projectge.DAO.Membre_departementRepository;
import com.example.projectge.DAO.UserRepository;
import com.example.projectge.models.Departement;
import com.example.projectge.models.Membre_departement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MembreDepartServiceImp implements MembreDepartService{
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private Membre_departementRepository membre_departementRepository;


    @Override
    public Membre_departement findMembreByUserName(String username) {
        var user = userRepository.findUserByUsername(username);
        return membre_departementRepository.findMembre_departementByCompte(user);
    }

    @Override
    public List<Membre_departement> findByDep(Departement departement) {
        return membre_departementRepository.findMembre_departementByDepartement(departement);
    }

    @Override
    public String memberPost(Membre_departement membre_departement) {
        var roles = membre_departement.getCompte().getRole();
        var isChef = roles.stream().anyMatch(r -> r.getRoleName().equals("CHEF_DEP"));
        var isProf = roles.stream().anyMatch(r -> r.getRoleName().equals("PROF"));
        var isAdmin = roles.stream().anyMatch(r -> r.getRoleName().equals("ADMIN"));
        return (isProf)?"professeur":(isAdmin)?"administrative":"chef de departement";
    }


}
