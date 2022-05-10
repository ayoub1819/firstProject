package com.example.projectge.models;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Inheritance(strategy=InheritanceType.JOINED)
@DiscriminatorColumn(name="Poste")
@Data @AllArgsConstructor @NoArgsConstructor
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME,include = JsonTypeInfo.As.PROPERTY,property = "type")
@JsonSubTypes({ @JsonSubTypes.Type(value = prof.class , name = "prof"),@JsonSubTypes.Type(value = Membre_departement.class , name = "membre") })
public class Membre_departement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String cin;
    @OneToOne
    private User compte;

    @ManyToOne
    private Departement departement;

    private String nom;
    private String Prenom;

/*
    public Membre_departement(Long CNE, User compte, Departement departement, String nom, String prenom) {
        this.CIN = CNE;
        this.compte = compte;
        this.departement = departement;
        this.nom = nom;
        Prenom = prenom;
    }



    public Membre_departement(){

    }

    public Long getCIN() {
        return CIN;
    }

    public void setCIN(Long CIN) {
        this.CIN = CIN;
    }

    public User getCompte() {
        return compte;
    }

    public void setCompte(User compte) {
        this.compte = compte;
    }

    public Departement getDepartement() {
        return departement;
    }

    public void setDepartement(Departement departement) {
        this.departement = departement;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return Prenom;
    }

    public void setPrenom(String prenom) {
        Prenom = prenom;
    }*/
}
