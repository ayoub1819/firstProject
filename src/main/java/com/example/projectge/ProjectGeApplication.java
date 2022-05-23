package com.example.projectge;

import com.example.projectge.DAO.*;
import com.example.projectge.models.*;
import com.example.projectge.service.AccountService;
import com.example.projectge.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@SpringBootApplication
public class ProjectGeApplication implements CommandLineRunner {
    @Autowired
    private DepartementRepository departementRepository;
    @Autowired
    private AccountService accountService;
    @Autowired
    private Membre_departementRepository membre_departementRepository;
    @Autowired
    private RessourceRepository ressourceRepository;
    @Autowired
    private AffectationRepository affectationRepository;
    @Autowired
    private FournisseurRepository fournisseurRepository;
    @Autowired
    private EmailService senderService;
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
        accountService.saveUser(new User(null,"tech","tech@tech.com","1234",null));
        accountService.saveUser(new User(null,"four","four@four.com","1234",null));
        accountService.saveRole(new Role(null,"RESP"));
        accountService.saveRole(new Role(null,"MEM_DEP"));
        accountService.saveRole(new Role(null,"ADMIN"));
        accountService.saveRole(new Role(null,"PROF"));
        accountService.saveRole(new Role(null,"CHEF_DEP"));
        accountService.saveRole(new Role(null,"FOURNISSEUR"));
        accountService.saveRole(new Role(null,"MAINTENANCE"));
        accountService.addRoleToUser("responsable","RESP");
        accountService.addRoleToUser("admin","MEM_DEP");
        accountService.addRoleToUser("admin","ADMIN");
        accountService.addRoleToUser("prof","PROF");
        accountService.addRoleToUser("prof","MEM_DEP");
        accountService.addRoleToUser("chef","CHEF_DEP");
        accountService.addRoleToUser("chef","MEM_DEP");
        accountService.addRoleToUser("tech","MAINTENANCE");
        accountService.addRoleToUser("four","FOURNISSEUR");

        var fournisseur = new Fournisseur();
        fournisseur.setCIN("C22222");
        fournisseur.setGerant("john weak");
        fournisseur.setSociete("Alpha Tech");
        fournisseur.setLieu("fes");
        fournisseur.setTele("0584124795");
        fournisseur.setCompte(accountService.findUserByUserName("four"));




        var membre = new Membre_departement();
        membre.setCin("c1234");
        membre.setCompte(accountService.findUserByUserName("admin"));
        membre.setDepartement(depart);
        membre.setNom("ayoub");
        membre.setPrenom("ayoub");
        var membre2 = new Membre_departement();
        membre2.setCin("c123");
        membre2.setCompte(accountService.findUserByUserName("chef"));
        membre2.setDepartement(depart);
        membre2.setNom("zahi");
        membre2.setPrenom("zahi");
        departementRepository.save(depart);
        membre_departementRepository.save(membre);
        membre2 = membre_departementRepository.save(membre2);

        var now = new Date();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = dateFormat.format(now);

        Imprimente imprimente = new Imprimente();
        imprimente.setDate_liv(strDate);
        imprimente.setGarantie("6");
        imprimente.setMarque("canon");
        imprimente.setResolution(1000);
        imprimente.setVitesse(50L);
        imprimente.setFournisseur(fournisseur);
        fournisseurRepository.save(fournisseur);
        imprimente = ressourceRepository.save(imprimente);
        Ordinateur ordinateur = new Ordinateur();
        ordinateur.setDate_liv(strDate);
        ordinateur.setGarantie("-2");
        ordinateur.setCPU("I3");
        ordinateur.setDisque_dur(1000L);
        ordinateur.setEcran("LCD");
        ordinateur.setMarque("HP");
        ordinateur.setRAM(8L);
        ordinateur.setFournisseur(fournisseurRepository.findFournisseurByCIN("C22222"));
        ordinateur = ressourceRepository.save(ordinateur);
        Affectation affectation1 = new Affectation();
        affectation1.setDate("14/04/2022");
        affectation1.setDepartement(membre2.getDepartement().getId_departement());
        affectation1.setRessource(ordinateur.getId());
        affectation1.setMemmbre(membre2.getId());
        Affectation affectation2 = new Affectation();
        affectation2.setDate("14/04/2022");
        affectation2.setDepartement(membre2.getDepartement().getId_departement());
        affectation2.setRessource(imprimente.getId());
        affectation2.setMemmbre(membre2.getId());
        affectationRepository.save(affectation1);
        affectationRepository.save(affectation2);





    }
    @EventListener(ApplicationReadyEvent.class)
    public void sendEmail(){
        senderService.sendEmail("meryamserhane2017@gmail.com","meryem.serhane@usmba.ac.ma","test","succes bravo");
    }
}
