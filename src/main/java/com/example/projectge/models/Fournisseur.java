package com.example.projectge.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
public class Fournisseur {

    @Id

    private String CIN;

    private String gerant;
    private String lieu;

    @OneToOne
    private User compte;

    private String societe;
    private Long tele;

    public Fournisseur() {
    }

    public Fournisseur(String gerant, String lieu, User compte, String societe, Long tele) {
        this.gerant = gerant;
        this.lieu = lieu;
        this.compte = compte;
        this.societe = societe;
        this.tele = tele;
    }
    public Fournisseur(String CNE, String gerant, String lieu, User compte, String societe, Long tele) {
        this.CIN = CNE;
        this.gerant = gerant;
        this.lieu = lieu;
        this.compte = compte;
        this.societe = societe;
        this.tele = tele;
    }

    public String getCIN() {
        return CIN;
    }

    public void setCIN(String CNE) {
        this.CIN = CNE;
    }

    public String getGerant() {
        return gerant;
    }

    public void setGerant(String gerant) {
        this.gerant = gerant;
    }

    public String getLieu() {
        return lieu;
    }

    public void setLieu(String lieu) {
        this.lieu = lieu;
    }

    public User getCompte() {
        return compte;
    }

    public void setCompte(User compte) {
        this.compte = compte;
    }

    public String getSociete() {
        return societe;
    }

    public void setSociete(String societe) {
        this.societe = societe;
    }

    public Long getTele() {
        return tele;
    }

    public void setTele(Long tele) {
        this.tele = tele;
    }
}
