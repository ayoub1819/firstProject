package com.example.projectge.models;


import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class Responsable_ressources {

    @Id
    private String CIN;
    @OneToOne
    private User compte;
    private String nom;
    private String prenom;

    public Responsable_ressources(String CIN, User compte, String nom, String prenom) {
        this.CIN = CIN;
        this.compte = compte;
        this.nom = nom;
        this.prenom = prenom;
    }

    public Responsable_ressources(User compte, String nom, String prenom) {
        this.compte = compte;
        this.nom = nom;
        this.prenom = prenom;
    }

    public Responsable_ressources() {

    }

    public String getCIN() {
        return CIN;
    }

    public void setCIN(String CIN) {
        this.CIN = CIN;
    }

    public User getCompte() {
        return compte;
    }

    public void setCompte(User compte) {
        this.compte = compte;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }
}
