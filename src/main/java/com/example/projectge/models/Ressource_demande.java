package com.example.projectge.models;

import javax.persistence.*;


@Entity
@Inheritance(strategy= InheritanceType.JOINED)
@DiscriminatorColumn(name="type")
public abstract class Ressource_demande {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long quantite;


    public Ressource_demande(Long id, Long quantite) {
        this.id = id;
        this.quantite = quantite;
    }



    public Ressource_demande(Long quantite) {
        this.quantite = quantite;
    }


    public Ressource_demande(){ }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getQuantite() {
        return quantite;
    }

    public void setQuantite(Long quantite) {
        this.quantite = quantite;
    }
}
