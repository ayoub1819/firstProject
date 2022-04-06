package com.example.projectge.models;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


@Entity
public class Departement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_departement;

    private String Nom_departement;



    public Departement(){

    }
    public Departement(Long id_departement, String nom_departement) {
        this.id_departement = id_departement;
        Nom_departement = nom_departement;

    }

    public Departement(String nom_departement) {
        this.Nom_departement = nom_departement;

    }

    public Long getId_departement() {
        return id_departement;
    }

    public void setId_departement(Long id_departement) {
        this.id_departement = id_departement;
    }

    public String getNom_departement() {
        return Nom_departement;
    }

    public void setNom_departement(String nom_departement) {
        Nom_departement = nom_departement;
    }
}
