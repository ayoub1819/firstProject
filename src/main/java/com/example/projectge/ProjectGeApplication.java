package com.example.projectge;

import com.example.projectge.DAO.DepartementRepository;
import com.example.projectge.DAO.Membre_departementRepository;
import com.example.projectge.DAO.UserRepository;
import com.example.projectge.models.Departement;
import com.example.projectge.models.ERole;
import com.example.projectge.models.Membre_departement;
import com.example.projectge.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ProjectGeApplication implements CommandLineRunner {
    @Autowired
    private DepartementRepository departementRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private Membre_departementRepository membre_departementRepository;
    public static void main(String[] args) {
        SpringApplication.run(ProjectGeApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        var depart = new Departement("Info");
        var user = new User("admin","admin@admin.com","1234");
        user.setRoles(ERole.ROLE_ADMINISTRATIVE);
        var membre = new Membre_departement();
        membre.setCIN(12345L);
        membre.setCompte(user);
        membre.setDepartement(depart);
        membre.setNom("ayoub");
        membre.setPrenom("ayoub");
        departementRepository.save(depart);
        userRepository.save(user);
        membre_departementRepository.save(membre);


    }
}
