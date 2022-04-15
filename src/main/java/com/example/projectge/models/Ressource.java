package com.example.projectge.models;


import javax.persistence.*;

@Entity
@Inheritance(strategy=InheritanceType.JOINED)
@DiscriminatorColumn(name="type")
public  class  Ressource {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne(cascade = {CascadeType.ALL})
    private Fournisseur fournisseur;
    private String date_liv;
    private String garantie;
    private String id_fournisseur;
    private boolean affecte=false;


    public void setId_fournisseur(String id_fournisseur) {
        this.id_fournisseur = id_fournisseur;
    }

    public String getId_fournisseur(){
        return id_fournisseur;
    }

    public void setAffecte(boolean affecte) {
        this.affecte = affecte;
    }

    public boolean getAffecte(){
        return affecte;
    }

    public Ressource() { }

    public Ressource(String date_liv, String garantie, String id_fournisseur) {
        this.date_liv = date_liv;
        this.garantie = garantie;
        this.id_fournisseur = id_fournisseur;
    }

    public Ressource(Fournisseur fournisseur, String date_liv, String garantie) {
        this.fournisseur = fournisseur;
        this.date_liv = date_liv;
        this.garantie = garantie;
    }
    public Ressource(Long id, Fournisseur fournisseur, String date_liv, String garantie) {
        this.id = id;
        this.fournisseur = fournisseur;
        this.date_liv = date_liv;
        this.garantie = garantie;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Fournisseur getFournisseur() {
        return fournisseur;
    }

    public void setFournisseur(Fournisseur fournisseur) {
        this.fournisseur = fournisseur;
    }

    public String getDate_liv() {
        return date_liv;
    }

    public void setDate_liv(String date_liv) {
        this.date_liv = date_liv;
    }

    public String getGarantie() {
        return garantie;
    }

    public void setGarantie(String garantie) {
        this.garantie = garantie;
    }
}
