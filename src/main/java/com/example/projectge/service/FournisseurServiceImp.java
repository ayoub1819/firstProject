package com.example.projectge.service;

import com.example.projectge.DAO.FournisseurRepository;
import com.example.projectge.models.Fournisseur;
import com.example.projectge.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FournisseurServiceImp implements FournisseurService{
    @Autowired
    private FournisseurRepository fournisseurRepository;
    @Autowired
    private AccountService accountService;

    @Override
    public Fournisseur find(String username) {
        User user = accountService.findUserByUserName(username);
        return fournisseurRepository.findFournisseurByCompte(user);
    }

    @Override
    public boolean isUserBlackList(String username) {
        Fournisseur fournisseur = find(username);
        if (fournisseur == null) return false;
        else return fournisseur.isBlackList();
    }

    @Override
    public List<Fournisseur> findAll() {
        return fournisseurRepository.findAll();
    }

    @Override
    public List<User> findAllUsersNonFournisseur() {
        List<User> users = accountService.findAll();
        List<User> nonFournisseur = new ArrayList<>();
        for (User user : users) {
            boolean isFournisseur = user.getRole().stream().anyMatch(r -> r.getRoleName().equals("FOURNISSEUR"));
            Fournisseur fournisseur = find(user.getUsername());
            if (fournisseur == null && isFournisseur)
                nonFournisseur.add(user);
        }

        return nonFournisseur;
    }

    @Override
    public void save(Fournisseur fournisseur) {
        fournisseurRepository.save(fournisseur);
    }
}
