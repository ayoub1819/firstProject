package com.example.projectge.models;

import javax.persistence.*;
import java.util.List;

@Entity
public class Besoin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    private Departement deparetement;
    private Boolean etat;
    private String date;
    @OneToOne
    private Membre_departement membre;
    private String ressource;

    public Besoin(){

    }
    public Besoin(Boolean etat, String date, Membre_departement membre, String ressource) {
        this.etat = etat;
        this.date = date;
        this.membre = membre;
        this.ressource = ressource;
    }


    public Besoin(Long id, Boolean etat, String date, Membre_departement membre, String ressource) {
        this.id = id;
        this.etat = etat;
        this.date = date;
        this.membre = membre;
        this.ressource = ressource;
    }



    public Besoin(Departement deparetement, Boolean etat, String date, String ressource) {
        this.deparetement = deparetement;
        this.etat = etat;
        this.date = date;
        this.ressource = ressource;
    }




    public Besoin(Long id, Departement deparetement, Boolean etat, String date, String ressource) {
        this.id = id;
        this.deparetement = deparetement;
        this.etat = etat;
        this.date = date;
        this.ressource = ressource;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Departement getDeparetement() {
        return deparetement;
    }

    public void setDeparetement(Departement deparetement) {
        this.deparetement = deparetement;
    }

    public Boolean getEtat() {
        return etat;
    }

    public void setEtat(Boolean etat) {
        this.etat = etat;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Membre_departement getMembre() {
        return membre;
    }

    public void setMembre(Membre_departement membre) {
        this.membre = membre;
    }

    public String getRessource() {
        return ressource;
    }

    public void setRessource(String ressource) {
        this.ressource = ressource;
    }
}
