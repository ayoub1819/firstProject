package com.example.projectge.service;

import com.example.projectge.DAO.DepartementRepository;
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
    @Autowired
    private DepartementRepository departementRepository;


    @Override
    public List<Membre_departement> findAll() {
        return membre_departementRepository.findAll();
    }

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

    @Override
    public void save(Membre_departement membre_departement) {
        Long id_departement = membre_departement.getDepartement().getId_departement();
        Departement departement = departementRepository.getById(id_departement);
        membre_departement.setDepartement(departement);
        membre_departementRepository.save(membre_departement);
    }

    @Override
    public Membre_departement findById(Long id){
        return membre_departementRepository.getById(id);
    }

   @Override
   public Membre_departement getById(Long id){
        return membre_departementRepository.getById(id);
   }

    @Override
    public void delete(Membre_departement membre_departement) {
        membre_departementRepository.delete(membre_departement);
    }



}
