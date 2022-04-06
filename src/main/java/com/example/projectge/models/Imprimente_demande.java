package com.example.projectge.models;


import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("imprimante_demande")
public class Imprimente_demande extends Ressource_demande{

    private Long resolition;
    private Long vitesse;

    public Imprimente_demande(Long id, Long quantite, Long resolition, Long vitesse) {
        super(id, quantite);
        this.resolition = resolition;
        this.vitesse = vitesse;
    }

    public Imprimente_demande(Long quantite, Long resolition, Long vitesse) {
        super(quantite);
        this.resolition = resolition;
        this.vitesse = vitesse;
    }

    public Imprimente_demande(){

    }

    public Long getResolition() {
        return resolition;
    }

    public void setResolition(Long resolition) {
        this.resolition = resolition;
    }

    public Long getVitesse() {
        return vitesse;
    }

    public void setVitesse(Long vitesse) {
        this.vitesse = vitesse;
    }
}
