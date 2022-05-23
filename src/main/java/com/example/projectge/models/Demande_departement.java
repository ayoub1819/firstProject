package com.example.projectge.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor
public class Demande_departement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String etat;
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private Departement departement;
    private String date;
    @OneToMany
    private List<Besoin> besoin;
/*
    public Demande_departement(etat_demande etat, Departement departement, String date, List<Besoin> besoin) {
        this.etat = etat;
        this.departement = departement;
        this.date = date;
        this.besoin = besoin;
    }



    public Demande_departement(Long id, etat_demande etat, Departement departement, String date, List<Besoin> besoin) {
        this.id = id;
        this.etat = etat;
        this.departement = departement;
        this.date = date;
        this.besoin = besoin;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public etat_demande getEtat() {
        return etat;
    }

    public void setEtat(etat_demande etat) {
        this.etat = etat;
    }

    public Departement getDepartement() {
        return departement;
    }

    public void setDepartement(Departement departement) {
        this.departement = departement;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<Besoin> getBesoin() {
        return besoin;
    }

    public void setBesoin(List<Besoin> besoin) {
        this.besoin = besoin;
    }
    */

}
