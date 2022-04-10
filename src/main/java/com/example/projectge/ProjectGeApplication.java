package com.example.projectge;

import com.example.projectge.DAO.DepartementRepository;
import com.example.projectge.DAO.Membre_departementRepository;
import com.example.projectge.DAO.UserRepository;
import com.example.projectge.models.*;
import com.example.projectge.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class ProjectGeApplication implements CommandLineRunner {
    @Autowired
    private DepartementRepository departementRepository;
    @Autowired
    private AccountService accountService;
    @Autowired
    private Membre_departementRepository membre_departementRepository;

    public static void main(String[] args) {
        SpringApplication.run(ProjectGeApplication.class, args);
    }
    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    @Override
    public void run(String... args) throws Exception {
        var depart = new Departement("Info");
        //var user = new User(null,"admin","admin@admin.com","1234",null);
        accountService.saveUser(new User(null,"responsable","respo@admin.com","1234",null));
        accountService.saveUser(new User(null,"admin","admin@admin.com","1234",null));
        accountService.saveUser(new User(null,"prof","prof@admin.com","1234",null));
        accountService.saveUser(new User(null,"chef","chef@admin.com","1234",null));
        accountService.saveRole(new Role(null,"RESP"));
        accountService.saveRole(new Role(null,"MEM_DEP"));
        accountService.saveRole(new Role(null,"ADMIN"));
        accountService.saveRole(new Role(null,"PROF"));
        accountService.saveRole(new Role(null,"CHEF_DEP"));
        accountService.saveRole(new Role(null,"FOURNISSEUR"));
        accountService.addRoleToUser("responsable","RESP");
        accountService.addRoleToUser("admin","MEM_DEP");
        accountService.addRoleToUser("admin","ADMIN");
        accountService.addRoleToUser("prof","PROF");
        accountService.addRoleToUser("prof","MEM_DEP");
        accountService.addRoleToUser("chef","CHEF_DEP");
        accountService.addRoleToUser("chef","MEM_DEP");


        var membre = new Membre_departement();
        membre.setCIN(12345L);
        membre.setCompte(accountService.findUserByUserName("admin"));
        membre.setDepartement(depart);
        membre.setNom("ayoub");
        membre.setPrenom("ayoub");
        var membre2 = new Membre_departement();
        membre2.setCIN(12346L);
        membre2.setCompte(accountService.findUserByUserName("chef"));
        membre2.setDepartement(depart);
        membre2.setNom("zahi");
        membre2.setPrenom("zahi");
        departementRepository.save(depart);
        membre_departementRepository.save(membre);
        membre_departementRepository.save(membre2);


    }
}
