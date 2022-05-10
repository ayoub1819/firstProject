package com.example.projectge.controllers;

import com.example.projectge.DAO.DepartementRepository;
import com.example.projectge.models.Departement;
import com.example.projectge.models.Membre_departement;
import com.example.projectge.models.User;
import com.example.projectge.models.prof;
import com.example.projectge.service.AccountService;
import com.example.projectge.service.MembreDepartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class MembersAccountsManagementController {
    @Autowired
    private AccountService accountService;
    @Autowired
    private MembreDepartService membreDepartService;
    @Autowired
    private DepartementRepository departementRepository;

    @GetMapping(path = "/accountManagement")
    public String accountManagementIndex(Model model){
        List<Departement> departements = departementRepository.findAll();
        List<Membre_departement> membres = membreDepartService.findAll();
        Map<Long,String[]> posts = new HashMap<>();
        membres.forEach(membre -> {
            boolean isChef = membre.getCompte().getRole().stream().anyMatch(r -> r.getRoleName().equals("CHEF_DEP"));
            boolean isProf = membre instanceof prof ;
            boolean isAdmin = !isChef && !isProf;
            var post_lab = new String[2];
            if (isChef){
                System.out.println("chef");
                post_lab[0] = "Chef de Departement";
                post_lab[1] = null;
                posts.put(membre.getId(),post_lab);
            }
            if (isAdmin){
                System.out.println("admin");
                post_lab[0] = "Administrative";
                post_lab[1] = null;
                posts.put(membre.getId(),post_lab);
            }
            if (isProf){
                post_lab[0] = "Professeur";
                post_lab[1] = ((prof) membre).getLaboratoire();
                posts.put(membre.getId(),post_lab);
            }

        });
        model.addAttribute("departements",departements);
        model.addAttribute("posts",posts);
        model.addAttribute("membres",membres);
        return "accountManagement/accountManagement";
    }


    @PostMapping(path = "/addExcel")
    @ResponseBody
    public String addMembersByExcel(@RequestBody List<Membre_departement> membre_departements){
        for (Membre_departement membre : membre_departements) {
            if (membre.getCin().equals("c1118")) System.out.println( ( (prof) membre).getLaboratoire() );
        }
        membre_departements.forEach( membre -> {

            boolean isChef = membre.getCompte().getRole().stream().anyMatch(r -> r.getRoleName().equals("CHEF_DEP"));
            boolean isProf = membre instanceof prof ;
            boolean isAdmin = !isChef && !isProf;
            membre.getCompte().setRole(new ArrayList<>());
            User user = membre.getCompte();
            accountService.saveUser(user);
            accountService.addRoleToUser(user.getUsername(),"MEM_DEP");
            if (isAdmin) accountService.addRoleToUser(user.getUsername(),"ADMIN");
            if (isChef) accountService.addRoleToUser(user.getUsername(),"CHEF_DEP");
            if (isProf) accountService.addRoleToUser(user.getUsername(),"PROF");
            Departement departement = departementRepository.getById(membre.getDepartement().getId_departement());
            membre.setDepartement(departement);
            membreDepartService.save(membre);

        });

        return "sucess";
    }
}
