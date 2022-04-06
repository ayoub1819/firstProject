package com.example.projectge.models;


import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("ordinateur_demande")
public class Ordinateur_demande extends Ressource_demande{

    private String CPU;
    private Long disque_dur;
    private String ecran;
    private Long RAM;

    public Ordinateur_demande(){

    }
    public Ordinateur_demande(Long quantite, String CPU, Long disque_dur, String ecran, Long RAM) {
        super(quantite);
        this.CPU = CPU;
        this.disque_dur = disque_dur;
        this.ecran = ecran;
        this.RAM = RAM;
    }


    public Ordinateur_demande(Long id, Long quantite, String CPU, Long disque_dur, String ecran, Long RAM) {
        super(id, quantite);
        this.CPU = CPU;
        this.disque_dur = disque_dur;
        this.ecran = ecran;
        this.RAM = RAM;
    }




}
