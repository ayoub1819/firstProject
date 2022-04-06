package com.example.projectge.models;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Affectation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long departement;

    private Long ressource;

    private String date;


    private Long memmbre;

    public Affectation(){

    }
    public Affectation(Long id, Long ressource, String date, Long memmbre) {
        this.id = id;
        this.ressource = ressource;
        this.date = date;
        this.memmbre = memmbre;
    }


    public Affectation(Long ressource, String date, Long memmbre) {
        this.ressource = ressource;
        this.date = date;
        this.memmbre = memmbre;
    }




    public Affectation(Long id, Long departement, Long ressource, String date) {
        this.id = id;
        this.departement = departement;
        this.ressource = ressource;
        this.date = date;
    }


    public Affectation(Long departement, Long ressource, String date) {
        this.departement = departement;
        this.ressource = ressource;
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDepartement() {
        return departement;
    }

    public void setDepartement(Long departement) {
        this.departement = departement;
    }

    public Long getRessource() {
        return ressource;
    }

    public void setRessource(Long ressource) {
        this.ressource = ressource;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Long getMemmbre() {
        return memmbre;
    }

    public void setMemmbre(Long memmbre) {
        this.memmbre = memmbre;
    }
}
