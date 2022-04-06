package com.example.projectge.models;



import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("imprimante")
public class Imprimente extends Ressource {

    private String marque;
    private Integer resolution;
    private Long vitesse;

    public Imprimente() {

    }

    public Imprimente(String date_liv, String garantie, String id_fournisseur, String marque, Integer resolution, Long vitesse) {
        super(date_liv, garantie, id_fournisseur);
        this.marque = marque;
        this.resolution = resolution;
        this.vitesse = vitesse;
    }

    public String getMarque() {
        return marque;
    }

    public void setMarque(String marque) {
        this.marque = marque;
    }

    public Integer getResolution() {
        return resolution;
    }

    public void setResolution(Integer resolution) {
        this.resolution = resolution;
    }

    public Long getVitesse() {
        return vitesse;
    }

    public void setVitesse(Long vitesse) {
        this.vitesse = vitesse;
    }
}